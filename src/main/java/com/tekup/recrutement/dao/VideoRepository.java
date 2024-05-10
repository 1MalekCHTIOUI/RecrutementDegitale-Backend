package com.tekup.recrutement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
