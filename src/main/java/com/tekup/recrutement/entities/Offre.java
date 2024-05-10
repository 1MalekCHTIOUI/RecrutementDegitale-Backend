package com.tekup.recrutement.entities;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekup.recrutement.dto.OffreDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nom;
    @Column
    private String sujet;
    @Column
    private String description;
    private String dateCreation;
    private String competences;
    @Column
    private String typeContrat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categorie_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Categorie categorie;

    @JsonIgnore
    @OneToMany(mappedBy = "offre")
    private List<QuestionOffre> questions;

    @JsonIgnore
    public OffreDTO getOffres() {
        OffreDTO offreDTO = new OffreDTO();
        offreDTO.setId(id);
        offreDTO.setNom(nom);
        offreDTO.setCompetences(competences);
        offreDTO.setSujet(sujet);
        offreDTO.setDescription(description);
        offreDTO.setTypeContrat(typeContrat);
        offreDTO.setDateCreation(dateCreation);
        offreDTO.setCategorieId(categorie.getId());
        offreDTO.setCategorieLibelle(categorie.getLibelle());
        offreDTO.setQuestions(questions);
        return offreDTO;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        // Include other non-circular fields in the calculation
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((sujet == null) ? 0 : sujet.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
        result = prime * result + ((competences == null) ? 0 : competences.hashCode());
        result = prime * result + ((typeContrat == null) ? 0 : typeContrat.hashCode());
        result = prime * result + ((categorie == null || categorie.getId() == null) ? 0 : categorie.getId().hashCode());
        return result;
    }
}
