package com.tekup.recrutement.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.dao.CategorieRepository;
import com.tekup.recrutement.dao.OffreRepository;
import com.tekup.recrutement.dto.OffreDTO;
import com.tekup.recrutement.entities.Categorie;
import com.tekup.recrutement.entities.Offre;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OffreServiceImpl implements OffreService {
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public List<OffreDTO> getAllOffres() {
        return offreRepository.findAll().stream().map(Offre::getOffres).collect(Collectors.toList());

    }

    @Override

    public Offre addOffre(OffreDTO offreDTO, Long categorieId) throws IOException {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(categorieId);
        if (optionalCategorie.isPresent()) {
            Offre offre = new Offre();
            offre.setNom(offreDTO.getNom());
            offre.setSujet(offreDTO.getSujet());
            offre.setCompetences(offreDTO.getCompetences());
            offre.setDescription(offreDTO.getDescription());
            offre.setTypeContrat(offreDTO.getTypeContrat());
            offre.setCategorie(optionalCategorie.get());
            return offreRepository.save(offre);
        }
        return null;

    }

    @Override
    public Optional<OffreDTO> getOffreById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOffreById'");
    }

    @Override
    public Offre updateOffre(OffreDTO offreDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOffre'");
    }

    @Override
    public void deleteOffre(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOffre'");
    }

}
