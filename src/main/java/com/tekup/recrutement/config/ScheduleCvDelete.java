package com.tekup.recrutement.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tekup.recrutement.entities.CV;
import com.tekup.recrutement.services.CvService;

@Configuration
@EnableScheduling
public class ScheduleCvDelete {

    private CvService cvService;

    @Scheduled(cron = "0 12 0 * * ?")
    public void checkForDeletion() {

        List<CV> allCVs = cvService.getAllCVs();
        System.out.println("Checking for Scheduled deletion");
        System.out.println("Checking for Scheduled deletion");
        System.out.println("Checking for Scheduled deletion");
        for (CV cv : allCVs) {
            if (cv.isAcceptedBySystem() == false && cv.isArchived() == false
                    && cv.getDeletionDate().compareTo(java.sql.Date.valueOf(LocalDate.now())) >= 0) {
                cvService.deleteCV(cv.getId());
            }
        }
    }
}