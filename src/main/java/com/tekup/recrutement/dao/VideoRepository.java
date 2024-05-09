package com.tekup.recrutement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.recrutement.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
