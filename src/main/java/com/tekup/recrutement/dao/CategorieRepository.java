package com.tekup.recrutement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
