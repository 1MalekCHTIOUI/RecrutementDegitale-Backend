package com.tekup.recrutement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tekup.recrutement.entities.Video;
import com.tekup.recrutement.services.VideoService;

@RestController
@RequestMapping("/api")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("video") MultipartFile file) {
        Video savedVideo = videoService.saveVideo(file);
        return ResponseEntity.ok().body(savedVideo);
    }
}
