package com.tekup.recrutement.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OffreDTO {
    private Long id;
    private String nom;
    private String sujet;
    private String description;
    private String competences;
    private String typeContrat;
    private Long categorieId;
    private String categorieLibelle;
}
