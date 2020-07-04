package com.project.CinemaApp.controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.CinemaApp.entity.Movie;
import com.project.CinemaApp.entity.Projection;
import com.project.CinemaApp.entity.dto.MovieDTO;
import com.project.CinemaApp.entity.dto.SearchMovieProjectionDTO;
import com.project.CinemaApp.service.MovieService;
import com.project.CinemaApp.service.ProjectionService;

@RestController
@RequestMapping(value = "/api/movies")
public class MovieController {

	private final MovieService service;
	private final ProjectionService projectionService;
	
    @Autowired
    public MovieController(MovieService service, ProjectionService projectionService) {
        this.service = service;
        this.projectionService = projectionService;
    }
    
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieDTO>> getMovies() {
        List<Movie> list = this.service.findAll();
        List<MovieDTO> DTOs = new ArrayList<>();

        for (Movie model : list) {
            MovieDTO DTO = new MovieDTO();
            DTO.averageRating = model.getAverageRating();
            DTO.description = model.getDescription();
            DTO.duration = model.getDuration();
            DTO.genre = model.getGenre();
            DTO.id = model.getId();
            DTO.title = model.getTitle();
            DTOs.add(DTO);
        }
        
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	@SuppressWarnings("deprecation")
	@PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,     // tip podataka koje metoda može da primi
            produces = MediaType.APPLICATION_JSON_VALUE)     // tip odgovora
    public ResponseEntity<List<MovieDTO>> search(@RequestBody SearchMovieProjectionDTO smpDTO) throws Exception {

		List<Movie> all = this.service.findAll();
		List<Movie> withTitle = new ArrayList<>();
		List<Movie> withDescription = new ArrayList<>();
		List<Movie> withGenre = new ArrayList<>();
		List<Movie> withPrice = new ArrayList<>();
		List<Movie> withAverageRating = new ArrayList<>();
		List<Movie> withDate = new ArrayList<>();
		List<Movie> withTime = new ArrayList<>();
		
		List<MovieDTO> DTOs = new ArrayList<>();
		
		
		if(!smpDTO.title.equals("")){
			for (int i = 0; i < all.size(); i++) {
				if(all.get(i).getTitle().toLowerCase().contains(smpDTO.title.toLowerCase())){
					withTitle.add(all.get(i));
				}
			}
		}
		else {
			withTitle = all;
		}
		
		if(!smpDTO.description.equals("")){
			for (int i = 0; i < withTitle.size(); i++) {
				if(withTitle.get(i).getDescription().toLowerCase().contains(smpDTO.description.toLowerCase())){
					withDescription.add(withTitle.get(i));
				}
			}
		}
		else {
			withDescription = withTitle;
		}
		
		if(!smpDTO.genre.equals("")){
			for (int i = 0; i < withDescription.size(); i++) {
				if(withDescription.get(i).getGenre().toLowerCase().contains(smpDTO.genre.toLowerCase())){
					withGenre.add(withDescription.get(i));
				}
			}
		}
		else {
			withGenre = withDescription;
		}
		
		if(!smpDTO.averageRatingMax.equals("") && !smpDTO.averageRatingMin.equals("")){
			double averageRatingMax = 0, averageRatingMin = 0;
			boolean parseError = false;
			try {
				averageRatingMax = Double.parseDouble(smpDTO.averageRatingMax);
				averageRatingMin = Double.parseDouble(smpDTO.averageRatingMin);
			}
			catch (NumberFormatException e){
			    parseError = true;
			}
			
			if(!parseError){
				for (int i = 0; i < withGenre.size(); i++) {
					double averageRating = Double.parseDouble(withGenre.get(i).getAverageRating());
					if(averageRating >= averageRatingMin && averageRating <= averageRatingMax) {
						withAverageRating.add(withGenre.get(i));
					}
				}
			}
		}
		else {
			withAverageRating = withGenre;
		}
		
		if(!smpDTO.priceMax.equals("") && !smpDTO.priceMin.equals("")){
			double priceMax = 0, priceMin = 0;
			boolean parseError = false;
			try {
				priceMax = Double.parseDouble(smpDTO.priceMax);
				priceMin = Double.parseDouble(smpDTO.priceMin);
			}
			catch (NumberFormatException e){
			    parseError = true;
			}
			
			if(!parseError){
				for (int i = 0; i < withAverageRating.size(); i++) {
					Long id = withAverageRating.get(i).getId();
					List<Projection> projections = projectionService.findAll().stream().filter(p -> p.getId() == id).collect(Collectors.toList());
					for (int k = 0; k < projections.size(); k++) {
						double price = projections.get(k).getPrice();
						if(price >= priceMin && price <= priceMax) {
							withPrice.add(withAverageRating.get(i));
							break;
						}
					}		
				}
			}
		}
		else {
			withPrice = withAverageRating;
		}
		
		//2020-06-20
		if(!smpDTO.date.equals("")){
			String[] parts = smpDTO.date.split("-");
			int year = Integer.parseInt(parts[0]);
			int month = Integer.parseInt(parts[1]);
			int day = Integer.parseInt(parts[2]);
			
			for (int i = 0; i < withPrice.size(); i++) {
				Long id = withPrice.get(i).getId();
				List<Projection> projections = projectionService.findAll().stream().filter(p -> p.getId() == id).collect(Collectors.toList());
				for (int k = 0; k < projections.size(); k++) {
					Calendar projectionDate = new GregorianCalendar();
					projectionDate.setTime(projections.get(k).getDateAndTime());
					int projectionYear = projectionDate.get(Calendar.YEAR);
					int projectionMonth = projectionDate.get(Calendar.MONTH) + 1;
					int projectionDay = projectionDate.get(Calendar.DAY_OF_MONTH);
					
					if(projectionYear == year && projectionMonth == month && projectionDay == day){
						withDate.add(withPrice.get(i));
						break;
					}
				}
			}
		}
		else {
			withDate = withPrice;
		}
		
		
		
		//17:00
		if(!smpDTO.time.equals("")){
			String[] parts = smpDTO.time.split(":");
			int hours = Integer.parseInt(parts[0]);
			int minutes = Integer.parseInt(parts[1]);
			
			for (int i = 0; i < withDate.size(); i++) {
				Long id = withDate.get(i).getId();
				List<Projection> projections = projectionService.findAll().stream().filter(p -> p.getId() == id).collect(Collectors.toList());
				for (int k = 0; k < projections.size(); k++) {
					java.util.Date projectionDate = projections.get(k).getDateAndTime();
					if(projectionDate.getHours() == hours && projectionDate.getMinutes() == minutes){
						withTime.add(withDate.get(i));
						break;
					}
				}
			}
		}
		else {
			withTime = withDate;
		}
		
		DTOs = convertMovieListToMovieDTOList(withTime);
		
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	//http://localhost:8080/api/movies/sort?sortBy=abc
	@GetMapping("/sort")
	@ResponseBody
	public ResponseEntity<List<MovieDTO>> sort(@RequestParam String sortBy) {
		List<MovieDTO> DTOs = convertMovieListToMovieDTOList(this.service.findAll());
		
		switch (sortBy) {
		case "title":
			Collections.sort(DTOs, new Comparator<MovieDTO>() {
			    public int compare(MovieDTO one, MovieDTO other) {
			        return one.title.compareTo(other.title);
			    }
			}); 
			break;
		case "averageRating":
			Collections.sort(DTOs, new Comparator<MovieDTO>() {
			    public int compare(MovieDTO one, MovieDTO other) {
			        return other.averageRating.compareTo(one.averageRating);
			    }
			}); 
			break;
			
		case "duration":
			Collections.sort(DTOs, new Comparator<MovieDTO>() {
			    public int compare(MovieDTO one, MovieDTO other) {
			        return one.duration.compareTo(other.duration);
			    }
			}); 
			break;
		
		case "genre":
			Collections.sort(DTOs, new Comparator<MovieDTO>() {
			    public int compare(MovieDTO one, MovieDTO other) {
			        return one.genre.compareTo(other.genre);
			    }
			}); 
			break;
		
		default:
			break;
		}
		
	    return new ResponseEntity<>(DTOs, HttpStatus.OK);
	}
	
	private List<MovieDTO> convertMovieListToMovieDTOList(List<Movie> inputList){
		List<MovieDTO> DTOs = new ArrayList<>();
		
		for (Movie model : inputList) {
            MovieDTO DTO = new MovieDTO();
            DTO.averageRating = model.getAverageRating();
            DTO.description = model.getDescription();
            DTO.duration = model.getDuration();
            DTO.genre = model.getGenre();
            DTO.id = model.getId();
            DTO.title = model.getTitle();
            DTOs.add(DTO);
        }
		
		return DTOs;
	}
}

