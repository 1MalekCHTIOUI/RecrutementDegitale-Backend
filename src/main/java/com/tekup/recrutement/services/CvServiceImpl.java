package com.tekup.recrutement.services;

import com.tekup.recrutement.dao.CvRepository;
import com.tekup.recrutement.entities.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class CvServiceImpl implements CvService {
    @Autowired
    private CvRepository cvRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public CV saveCV(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }
            String uuid = UUID.randomUUID().toString();

            String downloadUrl = generateDownloadUrl(uuid, fileName);
            CV cv = new CV(fileName, uuid, downloadUrl, file.getBytes());

            return cvRepository.save(cv);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + e.getMessage());
        }
    }

    private String generateDownloadUrl(String uuid, String fileName) {
        return "http://localhost:8080/cv/download/" + uuid + "/" + fileName;
    }

    @Override
    public CV getCV(Long cvId) throws Exception {
        return cvRepository
                .findById(cvId)
                .orElseThrow(
                        () -> new Exception("CV not found with Id: " + cvId));
    }

    public Optional<CV> findByUuid(String uuid) {
        return Optional.ofNullable(cvRepository.findByUuid(uuid));
    }
}
