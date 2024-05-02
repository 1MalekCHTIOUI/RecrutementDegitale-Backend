package com.tekup.recrutement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekup.recrutement.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
