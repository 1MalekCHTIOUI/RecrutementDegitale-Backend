package com.tekup.recrutement.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.DAO.QuestionRepository;

import com.tekup.recrutement.dto.QuestionDTO;

import com.tekup.recrutement.entities.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream().map(Question::getQuestions).collect(Collectors.toList());

    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

}
