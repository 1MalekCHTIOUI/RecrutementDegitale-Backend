package com.tekup.recrutement.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.tekup.recrutement.dto.OffreDTO;
import com.tekup.recrutement.entities.Offre;

public interface OffreService {
    public List<OffreDTO> getAllOffres();

    public Offre addOffre(OffreDTO offreDTO, Long categorieId, Long offreId) throws IOException;

    

    public Offre getOffreById(Long id);

    public OffreDTO updateOffre(Long id);

    public void deleteOffre(Long id);



    

}
