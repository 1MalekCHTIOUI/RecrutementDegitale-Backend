package com.tekup.recrutement.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekup.recrutement.dto.OffreDTO;
import com.tekup.recrutement.entities.Offre;
import com.tekup.recrutement.services.OffreService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/offres")
public class OffreController {
    @Autowired
    private OffreService offreService;

    @PostMapping("/addOffre/{categorieId}")
    public ResponseEntity<Offre> ajouterOffre(@PathVariable Long categorieId, @RequestBody OffreDTO offreDTO)
            throws IOException {
        Offre offre = offreService.addOffre(offreDTO, categorieId);
        return ResponseEntity.status(HttpStatus.CREATED).body(offre);
    }

    @GetMapping("")
    public ResponseEntity<List<OffreDTO>> getOffres() {
        List<OffreDTO> offres = offreService.getAllOffres();
        return new ResponseEntity<>(offres, HttpStatus.OK);
    }

}
