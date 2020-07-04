package com.project.CinemaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.CinemaApp.entity.Projection;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long>{

}