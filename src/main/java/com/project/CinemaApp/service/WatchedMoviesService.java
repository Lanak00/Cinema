package com.project.CinemaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CinemaApp.entity.WatchedMovies;
import com.project.CinemaApp.repository.WatchedMoviesRepository;


@Service
public class WatchedMoviesService {

    @Autowired
    private WatchedMoviesRepository repository;
 
    public WatchedMovies findOne(Long id) {
    	WatchedMovies wm = this.repository.getOne(id);
        return wm;
    }
 
	public List<WatchedMovies> findAll() {
		List<WatchedMovies> wm = this.repository.findAll();
	    return wm;
	}
	
	public WatchedMovies save(WatchedMovies wm) {
	    return this.repository.save(wm);
	}
	
	public void delete(Long id){
		WatchedMovies wm = this.repository.getOne(id);
		if(wm != null)
			this.repository.delete(wm);
	}
	
}
