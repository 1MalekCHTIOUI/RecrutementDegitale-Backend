package com.tekup.recrutement.DAO.repositories.ExamRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.DAO.entities.ExamEntities.Category;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

    public List <Quiz> findBycategory(Category category);

    public List <Quiz> findByActive(Boolean b);

    public List <Quiz> findBycategoryAndActive(Category c, Boolean b);

}
