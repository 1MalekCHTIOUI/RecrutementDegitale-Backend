package com.tekup.recrutement.controllers;

import com.tekup.recrutement.entities.CV;
import com.tekup.recrutement.services.CvServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/cv")
public class CvController {

    @Autowired
    private CvServiceImpl cvService;

    @PostMapping("/upload")
    public CV uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return cvService.saveCV(file);
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
}