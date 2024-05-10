package com.tekup.recrutement.Business.services.ExamServices;

import java.util.Set;

import com.tekup.recrutement.DAO.entities.ExamEntities.Question;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;

public interface QuestionService {

    public Question addQuestion(Question question);

    public Question updateQuestion(Question question);

    public Set<Question> getQuestions();

    public Question getQuestion(Long questionId);

    public Set<Question> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(Long questionId);

}
