package com.tekup.recrutement.DAO.entities;

public class QuizNotFoundException extends  RuntimeException{

    public QuizNotFoundException(String message) {
        super(message);
    }

}
