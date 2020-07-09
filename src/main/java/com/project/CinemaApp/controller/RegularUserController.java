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
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.entity.WatchedMovies;
import com.project.CinemaApp.entity.WatchedMoviesKey;
import com.project.CinemaApp.entity.dto.MovieDTO;
import com.project.CinemaApp.entity.dto.MovieWithPersonalRatingDTO;
import com.project.CinemaApp.entity.dto.ProjectionDTO;
import com.project.CinemaApp.entity.dto.ProjectionRegularUserDTO;
import com.project.CinemaApp.entity.dto.UserMovieRatingDTO;
import com.project.CinemaApp.entity.dto.UserProjectionDTO;
import com.project.CinemaApp.entity.dto.WatchedMoviesDTO;
import com.project.CinemaApp.service.MovieService;
import com.project.CinemaApp.service.ProjectionService;
import com.project.CinemaApp.service.UserService;
import com.project.CinemaApp.service.WatchedMoviesService;


@RestController
@RequestMapping(value = "/api/regularUsers")
public class RegularUserController {

	private final UserService service;
	private final MovieService movieService;
	private final ProjectionService projectionService;
	private final WatchedMoviesService watchedMoviesService;
	
    @Autowired
    public RegularUserController(UserService service, MovieService movieService, ProjectionService projectionService, WatchedMoviesService wmService) {
        this.service = service;
        this.movieService = movieService;
        this.projectionService = projectionService;
        this.watchedMoviesService = wmService;
    }
 
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") Long id) throws Exception {
		try {
			this.service.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
	@PostMapping(path = "/makeReservation" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void makeReservation(@RequestBody UserProjectionDTO userProjectionDTO) throws Exception {
		User user = this.service.findOne(userProjectionDTO.userId);
		Projection projection = this.projectionService.findOne(userProjectionDTO.projectionId);
		
		user.getProjectionsReserved().add(projection);
		projection.getUsersReserved().add(user);
		
        this.service.save(user);
    }
	
	@GetMapping(
            value = "/getReservations/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectionRegularUserDTO>> getResevations(@PathVariable(name = "id") Long id) {
        User user = this.service.findOne(id);
        Set<Projection> projections = user.getProjectionsReserved();
        
        List<ProjectionRegularUserDTO> DTOs = new ArrayList<>();
		
		for(Projection projection : projections) {
			Set<CinemaHall> cinemaHalls = projection.getHalls();
			Cinema cinema = null;
			for(CinemaHall ch : cinemaHalls) {  //nazalost nema drugog nacina da se uzme prvi element
				cinema = ch.getCinema();
				break;
			}
			
			ProjectionRegularUserDTO dto = convertProjectionToProjectionRegularUserDTO(projection, cinema);
			DTOs.add(dto);
		}
		
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	@GetMapping(
            value = "/getWatchedMovies/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieWithPersonalRatingDTO>> getWatchedMovies(@PathVariable(name = "id") Long id) {
        User user = this.service.findOne(id);
        Set<WatchedMovies> wachedMovies = user.getWatchedMovies();
        List<MovieWithPersonalRatingDTO> DTOs = new ArrayList<>();
		
		for(WatchedMovies wm : wachedMovies) {
			Movie movie = this.movieService.findOne(wm.getId().getMovieId());

			MovieWithPersonalRatingDTO DTO = new MovieWithPersonalRatingDTO();
            DTO.averageRating = movie.getAverageRating();
            DTO.description = movie.getDescription();
            DTO.duration = movie.getDuration();
            DTO.genre = movie.getGenre();
            DTO.id = movie.getId();
            DTO.title = movie.getTitle();
            DTO.personalRating = wm.getRating();
            DTOs.add(DTO);
		}
		
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	@PostMapping(path = "/checkReservation" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void checkReservation(@RequestBody UserProjectionDTO userProjectionDTO) throws Exception {
		User user = this.service.findOne(userProjectionDTO.userId);
		Projection projection = this.projectionService.findOne(userProjectionDTO.projectionId);
		WatchedMovies watchedMovie = new WatchedMovies();
		WatchedMoviesKey key = new WatchedMoviesKey();
		key.setMovieId(projection.getMovie().getId());
		key.setUserId(user.getId());
		watchedMovie.setId(key);
		watchedMovie.setMovie(projection.getMovie());
		watchedMovie.setUser(user);
		watchedMovie.setRating(-1);
		
        this.watchedMoviesService.save(watchedMovie);
        user.getProjectionsReserved().remove(projection);
        this.service.save(user);
    }
	
	@PostMapping(path = "/uncheckReservation" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void uncheckReservation(@RequestBody UserProjectionDTO userProjectionDTO) throws Exception {
		User user = this.service.findOne(userProjectionDTO.userId);
		Projection projection = this.projectionService.findOne(userProjectionDTO.projectionId);
		
        user.getProjectionsReserved().remove(projection);
        //projection.getUsersReserved().remove(user);
        this.service.save(user);
    }
	
	
	@PostMapping(path = "/rate" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void rate(@RequestBody UserMovieRatingDTO userMovieRatingDTO) throws Exception {
		User user = this.service.findOne(userMovieRatingDTO.userId);
		Movie movie = this.movieService.findOne(userMovieRatingDTO.movieId);
		int rating = userMovieRatingDTO.rating;
		
		Set<WatchedMovies> watchedMovies = movie.getUsersWatched();
		double sumOfRatings = 0;
		int count = 0;
		for(WatchedMovies wm : watchedMovies) {
			if(wm.getRating() != -1) {
				sumOfRatings += wm.getRating();
				count++;
			}	
			
			if(wm.getId().getUserId() == user.getId()) {
				wm.setRating(rating);
				this.watchedMoviesService.save(wm);
				sumOfRatings += wm.getRating();
				count++;
			}
		}
		
		double newAverageRating = sumOfRatings / count;
		
		movie.setAverageRating(Double.toString(newAverageRating));
		this.movieService.save(movie);
    }
	
	
	private ProjectionRegularUserDTO convertProjectionToProjectionRegularUserDTO(Projection projection, Cinema cinema) {
		ProjectionRegularUserDTO dto = new ProjectionRegularUserDTO();
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
		dto.cinemaName = cinema.getName();
		
		return dto;
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
	
}