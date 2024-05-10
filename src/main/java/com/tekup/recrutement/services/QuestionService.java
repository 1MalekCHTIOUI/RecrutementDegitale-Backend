package com.tekup.recrutement.services;

import java.util.List;
import java.util.Optional;

import com.tekup.recrutement.dto.QuestionDTO;
import com.tekup.recrutement.entities.Question;

public interface QuestionService {
    public List<QuestionDTO> getAllQuestions();

    public Optional<Question> getQuestionById(Long id);

    public Question updateQuestion(Question question);

    public void deleteQuestion(Long id);
}
