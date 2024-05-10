package com.tekup.recrutement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekup.recrutement.entities.QuestionOffre;

@Repository
public interface QuestionOffreRepository extends JpaRepository<QuestionOffre, Long> {

}
