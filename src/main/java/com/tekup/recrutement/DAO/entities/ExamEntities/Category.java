package com.tekup.recrutement.DAO.entities.ExamEntities;

import java.util.LinkedHashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cid;


    private String title; 

    private String description;


    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL )
    @JsonIgnore
    private java.util.Set<Quiz> quizzes = new LinkedHashSet<>(); 

    // public Category(String title, String description) {
    //     this.title = title;
    //     this.description = description;
        
    // }


}
