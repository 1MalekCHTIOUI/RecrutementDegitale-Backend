package com.tekup.recrutement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekup.recrutement.dto.CategorieDTO;
import com.tekup.recrutement.entities.Categorie;
import com.tekup.recrutement.services.CategorieService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/categories")
public class CategorieController {
    @Autowired
    private CategorieService categorieService;

    @PostMapping("/addCategorie")
    public ResponseEntity<Categorie> addCategorie(@RequestBody CategorieDTO categorieDTO) {
        Categorie categorie = categorieService.addCategorie(categorieDTO);
        return ResponseEntity.ok(categorie);
    }

    @GetMapping("")
    public ResponseEntity<List<CategorieDTO>> getCategories() {
        List<CategorieDTO> categories = categorieService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCategorieById(@PathVariable long id) {
        categorieService.deleteCategorie(id);

    }
}
