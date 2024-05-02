package com.tekup.recrutement.services;

import com.tekup.recrutement.entities.CV;
import org.springframework.web.multipart.MultipartFile;

public interface CvService {

    CV saveCV(MultipartFile cv) throws Exception;

    CV getCV(Long cvId) throws Exception;
}
