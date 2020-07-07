package com.project.CinemaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CinemaApp.entity.CinemaHall;
import com.project.CinemaApp.repository.CinemaHallRepository;


@Service
public class CinemaHallService {

    @Autowired
    private CinemaHallRepository cinemaRepository;
 
    public CinemaHall findOne(Long id) {
    	CinemaHall cinema = this.cinemaRepository.getOne(id);
        return cinema;
    }
 
	public List<CinemaHall> findAll() {
		List<CinemaHall> cinemas = this.cinemaRepository.findAll();
	    return cinemas;
	}
	
	public CinemaHall save(CinemaHall cinema) {
	    return this.cinemaRepository.save(cinema);
	}
	
	public void delete(Long id){
		CinemaHall cinema = this.cinemaRepository.getOne(id);
		if(cinema != null)
			this.cinemaRepository.delete(cinema);
	}
	
	
}
