package com.project.CinemaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CinemaApp.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
	 
}
