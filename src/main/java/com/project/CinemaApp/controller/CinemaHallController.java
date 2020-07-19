package com.project.CinemaApp.controller;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.project.CinemaApp.entity.dto.CinemaDTO;
import com.project.CinemaApp.entity.dto.CinemaHallDTO;
import com.project.CinemaApp.service.CinemaHallService;
import com.project.CinemaApp.service.CinemaService;
import com.project.CinemaApp.service.UserService;

@RestController
@RequestMapping(value = "/api/cinemaHalls")
public class CinemaHallController {

	private final CinemaService cinemaService;
	private final CinemaHallService service;
	
    @Autowired
    public CinemaHallController(CinemaHallService service, CinemaService cinemaService) {
		this.cinemaService = cinemaService;
		this.service = service;
    }
    
	
	@GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CinemaHallDTO>> getAllHallsOfCinema(@PathVariable(name = "id") Long id) {
        Cinema cinema = this.cinemaService.findOne(id);
        Set<CinemaHall> halls = cinema.getHalls();
        List<CinemaHallDTO> DTOs = new ArrayList<>();

        for (CinemaHall model : halls) {
        	CinemaHallDTO DTO = new CinemaHallDTO();
            DTO.capacity = model.getCapacity();
            DTO.hallMark = model.getHallMark();
            DTO.id = model.getId();
            
            DTOs.add(DTO);
        }
        
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	@GetMapping(
            value = "/getCinemaHall/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CinemaHallDTO> getCinemaHall(@PathVariable(name = "id") Long id) {
        CinemaHall cinemaHall = this.service.findOne(id);

        CinemaHallDTO cinemaHallDTO = new CinemaHallDTO();
        cinemaHallDTO.id = cinemaHall.getId();
        cinemaHallDTO.capacity = cinemaHall.getCapacity();
        cinemaHallDTO.hallMark = cinemaHall.getHallMark();
        
        return new ResponseEntity<>(cinemaHallDTO, HttpStatus.OK);
    }
	
	@PostMapping(path = "/modify" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void modifyCinemaHall(@RequestBody CinemaHallDTO cinemaHallDTO) throws Exception {
        CinemaHall cinemaHall = this.service.findOne(cinemaHallDTO.id);
        cinemaHall.setCapacity(cinemaHallDTO.capacity);
        cinemaHall.setHallMark(cinemaHallDTO.hallMark);
        
        this.service.save(cinemaHall);
    }
	
	@PostMapping(value = "/add/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void addCinemaHall(@RequestBody CinemaHallDTO cinemaHallDTO, @PathVariable(name = "id") Long id) throws Exception {
		CinemaHall cinemaHall = new CinemaHall();
		cinemaHall.setCapacity(cinemaHallDTO.capacity);
		cinemaHall.setHallMark(cinemaHallDTO.hallMark);
		cinemaHall.setCinema(this.cinemaService.findOne(id));
        
        this.service.save(cinemaHall);
    }
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteCinemaHall(@PathVariable("id") Long id) throws Exception {
		try {
			this.service.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
}