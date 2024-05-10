package com.tekup.recrutement.Business.services.ExamServices;

import java.util.Set;

import com.tekup.recrutement.DAO.entities.ExamEntities.Category;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;

public interface QuizService {

    public Quiz addQuiz(Quiz quiz);

    public Quiz updateQuiz(Quiz quiz);

    public Set<Quiz> getQuizzes();

    public Quiz getQuiz(Long quizId);

    public void deleteQuiz(Long quizId);

    public java.util.List<Quiz> getQuizzesByCategory(Category category);

    public java.util.List<Quiz> getActiveQuizzes();

    public java.util.List<Quiz> getActiveQuizzesOfCategory(Category c);

}
