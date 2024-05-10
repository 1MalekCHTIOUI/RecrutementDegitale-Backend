package com.tekup.recrutement.services;

import org.springframework.web.multipart.MultipartFile;

import com.tekup.recrutement.entities.Video;

public interface VideoService {
    public Video saveVideo(MultipartFile file);
}
