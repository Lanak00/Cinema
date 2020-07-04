package com.project.CinemaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CinemaApp.entity.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long>{
	
}
