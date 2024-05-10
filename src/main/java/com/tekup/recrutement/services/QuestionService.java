package com.tekup.recrutement.services;

import java.util.List;
import java.util.Optional;

import com.tekup.recrutement.dto.QuestionDTO;
import com.tekup.recrutement.entities.QuestionOffre;

public interface QuestionService {
    public List<QuestionDTO> getAllQuestions();

    public Optional<QuestionOffre> getQuestionById(Long id);

    public QuestionOffre updateQuestion(QuestionOffre question);

    public void deleteQuestion(Long id);
}
