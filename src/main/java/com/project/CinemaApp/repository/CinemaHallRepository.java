package com.project.CinemaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CinemaApp.entity.CinemaHall;

@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long>{
	
}