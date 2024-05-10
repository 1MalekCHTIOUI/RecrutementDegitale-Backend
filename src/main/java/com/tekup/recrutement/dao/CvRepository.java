package com.tekup.recrutement.DAO;

import com.tekup.recrutement.entities.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<CV, Long> {
    CV findByUuid(String uuid);

    int deleteAllByIsAcceptedBySystem(boolean acceptedBySystem);

    int deleteAllByIsAcceptedBySystemAndDeletionDateNotNull(boolean acceptedBySystem);

    int deleteAllByArchived(boolean archived);
}
