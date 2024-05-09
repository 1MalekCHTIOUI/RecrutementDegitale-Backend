package com.tekup.recrutement.services;

import java.util.List;
import java.util.Optional;

import com.tekup.recrutement.dto.CategorieDTO;
import com.tekup.recrutement.entities.Categorie;

public interface CategorieService {
    public Categorie addCategorie(CategorieDTO categorieDTO);

    public List<CategorieDTO> getAllCategories();

    public Optional<Categorie> getCategorieBYId(Long id);

    public Categorie updateCategorie(Categorie categorie);

    public void deleteCategorie(Long id);

}
