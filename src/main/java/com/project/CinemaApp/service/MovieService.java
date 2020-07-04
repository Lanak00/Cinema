package com.project.CinemaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.CinemaApp.entity.Movie;
import com.project.CinemaApp.repository.MovieRepository;


@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;
 
    public Movie findOne(Long id) {
        return this.repository.getOne(id);
    }
 
    public List<Movie> findAll() {
    	return this.repository.findAll();
    }

    public Movie save(Movie cinema) {
        return this.repository.save(cinema);
    }

}