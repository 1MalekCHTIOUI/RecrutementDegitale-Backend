package com.tekup.recrutement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekup.recrutement.entities.Offre;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

}
