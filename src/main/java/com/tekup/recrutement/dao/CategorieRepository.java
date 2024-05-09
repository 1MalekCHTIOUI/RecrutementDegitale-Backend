package com.tekup.recrutement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
