package com.tekup.recrutement.controllers;

import com.tekup.recrutement.entities.CV;
import com.tekup.recrutement.services.CvServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cv")
public class CvController {

    @Autowired
    private CvServiceImpl cvService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals("application/pdf")) {
            return new ResponseEntity<>("Invalid file type. Only PDF file is allowed.", HttpStatus.BAD_REQUEST);
        }
        try {
            CV cv = cvService.saveCV(file);
            return new ResponseEntity<>(cv, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not save file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping
    public List<CV> getAllCVs() {
        return cvService.getAllCVs();
    }
}