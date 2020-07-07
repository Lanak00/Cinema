package com.project.CinemaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.entity.Projection;
import com.project.CinemaApp.repository.ProjectionRepository;

@Service
public class ProjectionService {

    @Autowired
    private ProjectionRepository repository;
 
    public Projection findOne(Long id) {
        return this.repository.getOne(id);
    }
 
    public List<Projection> findAll() {
    	return this.repository.findAll();
    }

    public Projection save(Projection cinema) {
        return this.repository.save(cinema);
    }
    
    public void delete(Long id){
		Projection projection = this.repository.getOne(id);
		if(projection != null)
			this.repository.delete(projection);
	}
}
