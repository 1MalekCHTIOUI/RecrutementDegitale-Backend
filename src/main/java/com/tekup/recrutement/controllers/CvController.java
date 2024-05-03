package com.tekup.recrutement.controllers;

import com.tekup.recrutement.entities.CV;
import com.tekup.recrutement.services.CvServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cv")
public class CvController {

    @Autowired
    private CvServiceImpl cvService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCv(@RequestParam("file") MultipartFile file) {
        if (!Objects.equals(file.getContentType(), "application/pdf")) {
            return new ResponseEntity<>("Invalid file type. Only PDF file is allowed.", HttpStatus.BAD_REQUEST);
        }
        try {
            /* HARDCODED KEYWORDS WAITING FOR OFFER ENTITY TO BE CREATED */

            final List<String> tempKeywords = Arrays.asList("java", "spring", "angular", "react", "node", "javascript",
                    "sql", "html", "css", "github", "git");

            CV cv = cvService.saveCV(file, tempKeywords);
            return new ResponseEntity<>(cv, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download/{uuid}/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid, @PathVariable String fileName) {
        System.out.println("UUID: " + uuid + " Filename: " + fileName);
        Optional<CV> cvOptional = cvService.findByUuid(uuid);
        if (cvOptional.isPresent()) {
            CV cv = cvOptional.get();
            System.out.println(cv.getUrl().toString());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + cv.getNom())
                    .body(cv.getData());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{cvId}")
    public CV getCV(@PathVariable Long cvId) throws Exception {
        return cvService.getCV(cvId);
    }

    @GetMapping("/pdf/{cvId}")
    public ResponseEntity<?> getPDF(@PathVariable Long cvId) throws Exception {
        return cvService.getPDFfromCv(cvId);
    }

    @GetMapping
    public List<CV> getAllCVs() {
        return cvService.getAllCVs();
    }

    @GetMapping("/test/{id}")
    public String test(@PathVariable Long id) {
        return cvService.extractTextFromPDF(cvService.getCV(id).getData());
    }

}