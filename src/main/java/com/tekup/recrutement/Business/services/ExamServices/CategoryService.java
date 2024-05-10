package com.tekup.recrutement.Business.services.ExamServices;

import java.util.Set;

import com.tekup.recrutement.DAO.entities.ExamEntities.Category;

public interface CategoryService {
    
    public Category addCategory(Category category);

    public Category updateCategory(Category category);

    public Set<Category> getCategories();

    public Category getCategory(Long categoryId);

    public void deleteCategory(Long categoryId);

}
