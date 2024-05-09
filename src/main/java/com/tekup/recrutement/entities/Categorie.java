package com.tekup.recrutement.entities;

import java.io.Serializable;

import com.tekup.recrutement.dto.CategorieDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    public CategorieDTO getCategories() {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(id);
        categorieDTO.setLibelle(libelle);
        return categorieDTO;
    }

}