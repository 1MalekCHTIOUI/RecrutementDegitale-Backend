package com.tekup.recrutement.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.dao.CategorieRepository;
import com.tekup.recrutement.dao.OffreRepository;
import com.tekup.recrutement.dao.QuestionRepository;
import com.tekup.recrutement.dto.OffreDTO;
import com.tekup.recrutement.entities.Categorie;
import com.tekup.recrutement.entities.Offre;
import com.tekup.recrutement.entities.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OffreServiceImpl implements OffreService {
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<OffreDTO> getAllOffres() {
        return offreRepository.findAll().stream().map(Offre::getOffres).collect(Collectors.toList());

    }

    @Override
    public Offre addOffre(OffreDTO offreDTO, Long categorieId) throws IOException {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(categorieId);
        Offre offre = new Offre();
        offre.setNom(offreDTO.getNom());
        offre.setSujet(offreDTO.getSujet());
        offre.setCompetences(offreDTO.getCompetences());
        offre.setDescription(offreDTO.getDescription());
        offre.setTypeContrat(offreDTO.getTypeContrat());
        offre.setDateCreation(offre.getDateCreation());
        offre.setCategorie(optionalCategorie.get());
        offre.setQuestions(offreDTO.getQuestions());

        Offre savedOffre = offreRepository.save(offre);
        List<Question> questions = offreDTO.getQuestions();

        for (Question question : questions) {
            question.setOffre(savedOffre);
            questionRepository.save(question);
        }

        savedOffre.setQuestions(questions);
        offreRepository.save(savedOffre);

        return savedOffre;

    }

    @Override
    public Optional<OffreDTO> getOffreById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOffreById'");
    }

    @Override
    public OffreDTO updateOffre(Long id) {
        Optional<Offre> optionalOffre = offreRepository.findById(id);
        if (optionalOffre.isPresent()) {
            Offre offre = optionalOffre.get();
            return offre.getOffres();
        }
        return null;
    }

    @Override
    public void deleteOffre(Long id) {
        Optional<Offre> optionalOffre = offreRepository.findById(id);
        if (optionalOffre.isEmpty())
            throw new IllegalArgumentException("offre with id" + id + "not found");
        offreRepository.deleteById(id);

    }

}