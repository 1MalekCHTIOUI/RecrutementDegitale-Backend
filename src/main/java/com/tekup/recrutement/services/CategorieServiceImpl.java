package com.tekup.recrutement.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.dao.CategorieRepository;
import com.tekup.recrutement.dto.CategorieDTO;
import com.tekup.recrutement.entities.Categorie;

@Service

public class CategorieServiceImpl implements CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public Optional<Categorie> getCategorieBYId(Long id) {
        return categorieRepository.findById(id);
    }

    @Override
    public Categorie updateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    public Categorie addCategorie(CategorieDTO categorieDTO) {
        Categorie categorie = new Categorie();
        categorie.setId(categorieDTO.getId());
        categorie.setLibelle(categorieDTO.getLibelle());
        return categorieRepository.save(categorie);
    }

    @Override
    public List<CategorieDTO> getAllCategories() {

        return categorieRepository.findAll().stream().map(Categorie::getCategories).collect(Collectors.toList());
    }

}
