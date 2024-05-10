package com.tekup.recrutement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.tekup.recrutement.dto.QuestionDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class QuestionOffre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private int duree;
    @JsonIgnore
    @ManyToOne
    private Offre offre;

    public QuestionDTO getQuestions() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        questionDTO.setDuree(duree);
        questionDTO.setLibelle(libelle);

        return questionDTO;
    }
}
