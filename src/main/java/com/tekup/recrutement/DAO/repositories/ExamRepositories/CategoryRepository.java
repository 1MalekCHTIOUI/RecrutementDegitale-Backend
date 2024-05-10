package com.tekup.recrutement.DAO.repositories.ExamRepositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.DAO.entities.ExamEntities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
