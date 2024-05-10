package com.tekup.recrutement.DAO.repositories.ExamRepositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.DAO.entities.ExamEntities.Question;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    Set<Question> findByQuiz(Quiz quiz);

}
