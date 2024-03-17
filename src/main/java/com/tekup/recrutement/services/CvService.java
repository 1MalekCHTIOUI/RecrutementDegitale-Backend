package com.tekup.recrutement.services;

import com.tekup.recrutement.entities.CV;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CvService {

    CV saveCV(MultipartFile cv) throws Exception;

    Optional<CV> findByUuid(String uuid);

    List<CV> getAllCVs();

    CV getCV(Long cvId) throws Exception;
}
