package com.tekup.recrutement.services;

import com.tekup.recrutement.dao.CvRepository;
import com.tekup.recrutement.dao.OffreRepository;
import com.tekup.recrutement.dto.OffreDTO;
import com.tekup.recrutement.dto.UserDTO;
import com.tekup.recrutement.entities.CV;
import com.tekup.recrutement.entities.Offre;
import com.tekup.recrutement.entities.User;

import jakarta.annotation.PostConstruct;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class CvServiceImpl implements CvService {
    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OffreRepository offreRep;

    @SuppressWarnings("unchecked")
    @Override
    public CV saveCV(MultipartFile file, Long userId, Long offreId,
            List<String> optionalKeywords)
            throws Exception {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            User user = userService.getUser(userId);
            Optional<Offre> offre = offreRep.findById(offreId);

            String[] competences = offre.get().getCompetences().split(",");

            List<String> obligatorySkills = Arrays.asList(competences);

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }
            String uuid = UUID.randomUUID().toString();

            String downloadUrl = generateDownloadUrl(uuid, fileName);
            LocalDate today = LocalDate.now(); // Get current date
            LocalDate oneWeekAfter = today.plusDays(7); // Add 7 days
            Date oneWeekAfterDate = java.sql.Date.valueOf(oneWeekAfter);
            CV cv = new CV(fileName, uuid, downloadUrl, file.getBytes(), new Date(), null, 0, null, "",
                    false, false, user, offre.orElse(null));

            Object scoreParTech = giveScore(cv.getData(), obligatorySkills, optionalKeywords);

            Engineer eng = scoreParSpec(extractTextFromPDF(cv.getData()));

            cv.setSpecialite(eng.specialite);

            cv.setScore((int) ((Map<String, Object>) scoreParTech).get("score"));

            if (cv.getScore() > 0) {
                cv.setScore(cv.getScore() + eng.score);
                cv.setAcceptedBySystem(true);

            } else
                cv.setDeletionDate(oneWeekAfterDate);

            cv.setSkillsFound((List<String>) ((Map<String, Object>) scoreParTech).get("keywords"));
            // if (cv.getScore() == 0) {
            // throw new Exception("Les exigences de l'offre ne sont pas remplies");
            // }
            CV test = cvRepository.save(cv);
            test.setData(null);
            return test;

        } catch (Exception e) {
            throw new Exception("Could not save File: " + e.getMessage());
        }
    }

    @Override
    public CV getCV(Long cvId) {
        Optional<CV> cvOptional = cvRepository.findById(cvId);
        return cvOptional.orElse(null);
    }

    @Override
    public ResponseEntity<?> deleteCV(Long cvId) {
        Optional<CV> cvOptional = cvRepository.findById(cvId);
        if (cvOptional.isPresent()) {
            cvRepository.deleteById(cvId);
            return new ResponseEntity<>("CV deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCVs(boolean status) {
        try {
            int x = cvRepository.deleteAllByIsAcceptedBySystem(status);
            if (x > 0)
                return ResponseEntity.ok().build();
            else
                return new ResponseEntity<>("CV status is not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting CVs: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteArchivedCVs() {
        try {
            int x = cvRepository.deleteAllByArchived(true);
            if (x > 0)
                return ResponseEntity.ok().build();
            else
                return new ResponseEntity<>("No archived CVs found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting archived CVs: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @Transactional
    // public ResponseEntity<?> deleteRejectedCVsWithNoAction() {
    // try {
    // int x =
    // cvRepository.deleteAllByIsAcceptedBySystemAndDeletionDateNotNull(false);
    // if (x > 0)
    // return ResponseEntity.ok().build();
    // else
    // return new ResponseEntity<>("No Rejected cvs with no action found",
    // HttpStatus.NOT_FOUND);
    // } catch (Exception e) {
    // return new ResponseEntity<>("Error deleting CVs: " + e.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    @PostConstruct
    public void checkForDeletion() {
        List<CV> allCVs = cvRepository.findAll();
        for (CV cv : allCVs) {
            if (cv.isAcceptedBySystem() == false && cv.isArchived() == false
                    && cv.getDeletionDate().before(java.sql.Date.valueOf(LocalDate.now()))) {
                cvRepository.deleteById(cv.getId());
            }
        }
    }

    @Override
    public Optional<CV> findByUuid(String uuid) {
        return Optional.ofNullable(cvRepository.findByUuid(uuid));
    }

    @Override
    public List<CV> getAllCVs() {
        return cvRepository.findAll();
    }

    @Override
    public CV archiveCV(Long cvId) {
        Optional<CV> cvOptional = cvRepository.findById(cvId);
        if (cvOptional.isPresent()) {
            CV cv = cvOptional.get();
            cv.setArchived(true);
            return cvRepository.save(cv);
        }
        return null;
    }

    private static final HashMap<String, Integer> ENGINEERING_KEYWORDS = new HashMap<String, Integer>() {
        {
            put("ingénieur", 5);
            put("développeur", 2);
        }
    };

    private String generateDownloadUrl(String uuid, String fileName) {
        return "http://localhost:8080/cv/download/" + uuid + "/" + fileName;
    }

    private static Engineer scoreParSpec(String cvText) {
        int score = 0;
        String spec = "";
        String lowercaseCvText = cvText.toLowerCase();
        for (Map.Entry<String, Integer> entry : ENGINEERING_KEYWORDS.entrySet()) {
            String keyword = entry.getKey();
            int keywordScore = entry.getValue();
            if (lowercaseCvText.contains(keyword)) {
                score += keywordScore;
                spec = keyword;
            }
        }
        int threshold = 5;
        Engineer eng = new Engineer();
        eng.specialite = spec;
        eng.score = score;
        return eng;
    }

    @Override
    public ResponseEntity<?> getPDFfromCv(Long cvId) {
        Optional<CV> cvOptional = cvRepository.findById(cvId);
        if (cvOptional.isPresent()) {
            CV cv = cvOptional.get();
            byte[] pdfData = cv.getData();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Adjust content type as needed
            headers.setContentDispositionFormData(cv.getNom(), cv.getNom()); // Adjust filename as needed

            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    public String extractTextFromPDF(byte[] pdfData) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfData))) {
            if (!document.isEncrypted()) {
                PDFTextStripper textStripper = new PDFTextStripper();
                return textStripper.getText(document);
            } else {
                throw new IllegalArgumentException("Encrypted PDF not supported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing PDF: " + e.getMessage());
        }
    }

    public Object giveScore(byte[] data, List<String> obligatoryKeywords, List<String> optionalKeywords) {
        String text = extractTextFromPDF(data);
        List<String> foundKeywords = new ArrayList<String>();
        int score = 0;
        for (String keyword : obligatoryKeywords) {
            System.out.println(keyword);

            if (text.toLowerCase().contains(keyword.toLowerCase())) {
                foundKeywords.add(keyword);
                score++;
            }
        }
        if (score > 0) {
            for (String keyword : optionalKeywords) {
                if (text.toLowerCase().contains(keyword.toLowerCase())) {
                    foundKeywords.add(keyword);
                    score++;
                }
            }
        }
        int finalScore = score;
        return new HashMap<String, Object>() {
            {
                put("score", finalScore);
                put("keywords", foundKeywords);
            }
        };
    }

    static class Engineer {
        public String specialite;
        public int score;
    }
}
