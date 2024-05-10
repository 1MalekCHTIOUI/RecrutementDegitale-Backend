package com.tekup.recrutement.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tekup.recrutement.DAO.VideoRepository;
import com.tekup.recrutement.entities.Video;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video saveVideo(MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            Video video = new Video();
            video.setData(data);
            video.setName(file.getOriginalFilename());
            video.setType(file.getContentType());
            video.setSize(file.getSize());
            return videoRepository.save(video);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
