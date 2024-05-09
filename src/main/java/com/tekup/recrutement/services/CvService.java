package com.tekup.recrutement.services;

import com.tekup.recrutement.entities.CV;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CvService {

    CV saveCV(MultipartFile file, Long userId, List<String> obligatoryKeywords, List<String> optionalKeywords)
            throws Exception;

    Optional<CV> findByUuid(String uuid);

    List<CV> getAllCVs();

    CV getCV(Long cvId) throws Exception;

    ResponseEntity<?> getPDFfromCv(Long cvId);

    ResponseEntity<?> deleteCV(Long cvId);

    ResponseEntity<?> deleteCVs(boolean status);

    // ResponseEntity<?> deleteRejectedCVsWithNoAction();

    CV archiveCV(Long cvId);
}
