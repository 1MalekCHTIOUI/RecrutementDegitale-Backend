package com.tekup.recrutement.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.DAO.QuestionOffreRepository;

import com.tekup.recrutement.dto.QuestionDTO;

import com.tekup.recrutement.entities.QuestionOffre;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionOffreServiceImpl implements QuestionService {
    @Autowired
    private QuestionOffreRepository questionRepository;

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream().map(QuestionOffre::getQuestions).collect(Collectors.toList());

    }

    @Override
    public Optional<QuestionOffre> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public QuestionOffre updateQuestion(QuestionOffre question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

}
