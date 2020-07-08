package com.project.CinemaApp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.entity.CinemaHall;
import com.project.CinemaApp.entity.Movie;
import com.project.CinemaApp.entity.Projection;
import com.project.CinemaApp.entity.dto.MovieDTO;
import com.project.CinemaApp.entity.dto.ProjectionAddDTO;
import com.project.CinemaApp.entity.dto.ProjectionDTO;
import com.project.CinemaApp.entity.dto.ProjectionRegularUserDTO;
import com.project.CinemaApp.service.CinemaHallService;
import com.project.CinemaApp.service.CinemaService;
import com.project.CinemaApp.service.MovieService;
import com.project.CinemaApp.service.ProjectionService;

@RestController
@RequestMapping(value = "/api/projections")
public class ProjectionController {

	private final CinemaHallService cinemaHallService;
	private final CinemaService cinemaService;
	private final ProjectionService service;
	private final MovieService movieService;
	
    @Autowired
    public ProjectionController(ProjectionService service, CinemaHallService cinemaHallService, CinemaService cinemaService, MovieService movieService) {
		this.cinemaHallService = cinemaHallService;
		this.cinemaService = cinemaService;
		this.service = service;
		this.movieService = movieService;
    }
    
    //PROMENI DA PREUZIMA IZ TABELE PROJEKCIJA NA OSNOVU ID BIOSKOPA
	@GetMapping(
            value = "/getAllProjectionsOfCinema/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectionDTO>> getAllProjectionsOfCinema(@PathVariable(name = "id") Long id) {
        Cinema cinema = this.cinemaService.findOne(id);
        Set<CinemaHall> halls = cinema.getHalls();
        List<ProjectionDTO> DTOs = new ArrayList<>();
        
        for(CinemaHall cinemaHall : halls) {
        	Set<Projection> projections = cinemaHall.getProjections();
        	for(Projection projection : projections) {
        		if(!isInProjections(projection.getId(), DTOs)) {
        			ProjectionDTO dto = createProjectionDTO(projection);
            		DTOs.add(dto);
        		}
        	}
        }
         
	
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	private boolean isInProjections(Long projectionId, List<ProjectionDTO> projections) {
		for(ProjectionDTO proj : projections) {
			if(proj.id == projectionId) {
				return true;
			}
		}
		return false;
	}
	
	
	@GetMapping(
            value = "/getProjection/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectionDTO> getCinemaHall(@PathVariable(name = "id") Long id) {
        Projection projection = this.service.findOne(id);       
        return new ResponseEntity<>(createProjectionDTO(projection), HttpStatus.OK);
    }
	
	@GetMapping(
            value = "/getProjectionsOfMovie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectionRegularUserDTO>> getProjectionsOfMovie(@PathVariable(name = "id") Long id) {
		Movie movie = this.movieService.findOne(id);
		Set<Projection> projections = movie.getProjections();
		List<ProjectionRegularUserDTO> DTOs = new ArrayList<>();
		
		for(Projection projection : projections) {
			Set<CinemaHall> cinemaHalls = projection.getHalls();
			Cinema cinema = null;
			for(CinemaHall ch : cinemaHalls) {  //nazalost nema drugog nacina da se uzme prvi element
				cinema = ch.getCinema();
				break;
			}
			
			
			ProjectionRegularUserDTO dto = new ProjectionRegularUserDTO();
			dto.movie = new MovieDTO();
			dto.movie.id = movie.getId();
			dto.movie.averageRating = movie.getAverageRating();
			dto.movie.description = movie.getDescription();
			dto.movie.duration = movie.getDuration();
			dto.movie.genre = movie.getGenre();
			dto.movie.title = movie.getTitle();
			dto.id = projection.getId();
			dto.price = projection.getPrice();
			dto.ticketsReserved = projection.getTicketsReserved();
			dto.dateAndTime = convertDateToString(projection.getDateAndTime());
			dto.cinemaName = cinema.getName();
			
			DTOs.add(dto);
		}
		
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	
	@PostMapping(path = "/modify" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void modifyProjection(@RequestBody ProjectionDTO projectionDTO) throws Exception {
        Projection projection = this.service.findOne(projectionDTO.id);
        projection.setPrice(projectionDTO.price);
        Date newDate = convertStringToDate(projectionDTO.dateAndTime);
        projection.setDateAndTime(newDate);
        
        this.service.save(projection);
    }
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void addProjection(@RequestBody ProjectionAddDTO projectionAddDTO) throws Exception {
		Projection projection = new Projection();
		projection.setPrice(projectionAddDTO.price);
		projection.setTicketsReserved(0);
		Date date = convertStringToDate(projectionAddDTO.dateAndTime);
		projection.setDateAndTime(date);
		
		for (Long cinemaHallId : projectionAddDTO.halls) {
			CinemaHall ch = this.cinemaHallService.findOne(cinemaHallId);
			ch.getProjections().add(projection);
			projection.getHalls().add(ch);
		}
		
		Movie movie = this.movieService.findOne(projectionAddDTO.movieId);
		projection.setMovie(movie);
		
        this.service.save(projection);
    }
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteCinemaHall(@PathVariable("id") Long id) throws Exception {
		try {
			this.service.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
	private String convertDateToString(Date date) {
		Calendar projectionDate = new GregorianCalendar();
		projectionDate.setTime(date);
		int projectionYear = projectionDate.get(Calendar.YEAR);
		int projectionMonth = projectionDate.get(Calendar.MONTH) + 1;
		int projectionDay = projectionDate.get(Calendar.DAY_OF_MONTH);
		
		return projectionYear + "-" + projectionMonth + "-" + projectionDay + " " + projectionDate.get(Calendar.HOUR) + ":" + projectionDate.get(Calendar.MINUTE);
	}
	
	private Date convertStringToDate(String date){
		String[] tokens1 = date.split(" ");
		String[] tokens2 = tokens1[0].split("-");
		String[] tokens3 = tokens1[1].split(":");
		int day = Integer.parseInt(tokens2[2]);
		int month = Integer.parseInt(tokens2[1]);
		int year = Integer.parseInt(tokens2[0]);
		int hours = Integer.parseInt(tokens3[0]);
		int minutes = Integer.parseInt(tokens3[1]);
		
		Date dateRet = new GregorianCalendar(year, month - 1, day, hours, minutes).getTime();
		return dateRet;
	}
	
	private ProjectionDTO createProjectionDTO(Projection projection) {
		ProjectionDTO dto = new ProjectionDTO();
		Movie movie = projection.getMovie();
		dto.movie = new MovieDTO();
		dto.movie.id = movie.getId();
		dto.movie.averageRating = movie.getAverageRating();
		dto.movie.description = movie.getDescription();
		dto.movie.duration = movie.getDuration();
		dto.movie.genre = movie.getGenre();
		dto.movie.title = movie.getTitle();
		dto.id = projection.getId();
		dto.price = projection.getPrice();
		dto.ticketsReserved = projection.getTicketsReserved();
		dto.dateAndTime = convertDateToString(projection.getDateAndTime());
		
		return dto;
	}
	
}