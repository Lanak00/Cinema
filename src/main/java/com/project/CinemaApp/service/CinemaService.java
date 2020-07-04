package com.project.CinemaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.repository.CinemaRepository;


@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;
 
    public Cinema findOne(Long id) {
	    Cinema cinema = this.cinemaRepository.getOne(id);
        return cinema;
    }
 
	public List<Cinema> findAll() {
		List<Cinema> cinemas = this.cinemaRepository.findAll();
	    return cinemas;
	}
	
	public Cinema save(Cinema cinema) {
	    return this.cinemaRepository.save(cinema);
	}
	
	public void delete(Long id){
		Cinema cinema = this.cinemaRepository.getOne(id);
		if(cinema != null)
			this.cinemaRepository.delete(cinema);
	}
	
	public void addMenagerToCinema(Long cinemaId, User manager){
		Cinema cinema = this.cinemaRepository.getOne(cinemaId);
		cinema.getManagers().add(manager);
		this.cinemaRepository.save(cinema);
	}
	 
}
