package com.tekup.recrutement.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekup.recrutement.dto.OffreDTO;
import com.tekup.recrutement.dto.QuestionDTO;
import com.tekup.recrutement.entities.QuestionOffre;
import com.tekup.recrutement.services.QuestionService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

@RequestMapping("/questions")
public class QuestionController {
    private QuestionService quesService;

    @GetMapping("")
    public ResponseEntity<List<QuestionDTO>> getQuestions() {
        List<QuestionDTO> questions = quesService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
