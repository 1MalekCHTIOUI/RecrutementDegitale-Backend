package com.tekup.recrutement.services;

import com.tekup.recrutement.dao.CvRepository;
import com.tekup.recrutement.entities.CV;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;


@Service
public class CvServiceImpl implements CvService {
    @Autowired
    private CvRepository cvRepository;


    @Override
    public CV saveCV(MultipartFile file, List<String> keywords) throws Exception {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }
            String uuid = UUID.randomUUID().toString();

            String downloadUrl = generateDownloadUrl(uuid, fileName);

            CV cv = new CV(fileName, uuid, downloadUrl, file.getBytes(), new Date(), 0, null, "");
            Object score = giveScore(cv.getData(), keywords);
            Engineer eng = scoreParSpec(extractTextFromPDF(cv.getData()));
            cv.setSpecialite(eng.specialite);
            cv.setScore((int) ((Map<String, Object>) score).get("score"));
            cv.setScore(cv.getScore() + eng.score);
            cv.setSkillsFound((List<String>) ((Map<String, Object>) score).get("keywords"));

            CV test = cvRepository.save(cv);
            test.setData(null);
            return test;
        } catch (Exception e) {
            throw new Exception("Could not save File: " + e.getMessage());
        }
    }

    private String generateDownloadUrl(String uuid, String fileName) {
        return "http://localhost:8080/cv/download/" + uuid + "/" + fileName;
    }

    @Override
    public CV getCV(Long cvId) {
        Optional<CV> cvOptional = cvRepository.findById(cvId);
        return cvOptional.orElse(null);
    }

    private static final HashMap<String, Integer> ENGINEERING_KEYWORDS = new HashMap<String, Integer>() {{
        put("ingénieur", 5);
        put("développeur", 2);
    }};


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

    @Override
    public Optional<CV> findByUuid(String uuid) {
        return Optional.ofNullable(cvRepository.findByUuid(uuid));
    }

    @Override
    public List<CV> getAllCVs() {
        return cvRepository.findAll();
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

    public Object giveScore(byte[] data, List<String> keywords) {
        String text = extractTextFromPDF(data);
        List<String> foundKeywords = new ArrayList<String>();
        int score = 0;
        for (String keyword : keywords) {
            if (text.toLowerCase().contains(keyword)) {
                foundKeywords.add(keyword);
                score++;
            }
        }
        int finalScore = score;
        return new HashMap<String, Object>() {{
            put("score", finalScore);
            put("keywords", foundKeywords);
        }};
    }

    static class Engineer {
        public String specialite;
        public int score;
    }
}
