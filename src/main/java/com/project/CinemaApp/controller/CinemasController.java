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
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.entity.dto.CinemaDTO;
import com.project.CinemaApp.entity.dto.CinemaHallDTO;
import com.project.CinemaApp.entity.dto.CinemaManagerDTO;
import com.project.CinemaApp.entity.dto.UserDTO;
import com.project.CinemaApp.service.CinemaService;
import com.project.CinemaApp.service.UserService;

@RestController
@RequestMapping(value = "/api/cinemas")
public class CinemasController {

	private final CinemaService service;
	private final UserService userService;
	
    @Autowired
    public CinemasController(CinemaService service, UserService userService) {
		this.service = service;
		this.userService = userService;
    }
    
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CinemaDTO>> getCinemas() {
        List<Cinema> list = this.service.findAll();
        List<CinemaDTO> DTOs = new ArrayList<>();
        
        for (Cinema model : list) {
        	CinemaDTO DTO = new CinemaDTO();
        	DTO.adress = model.getAdress();
            DTO.eMail = model.geteMail();
            DTO.id = model.getId();
            DTO.name = model.getName();
            DTO.phoneNumber = model.getPhoneNumber();
            DTOs.add(DTO);
        }
      
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	
	@GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CinemaDTO> getCinema(@PathVariable(name = "id") Long id) {
        Cinema cinema = this.service.findOne(id);

        CinemaDTO cinemaDTO = new CinemaDTO();
        cinemaDTO.adress = cinema.getAdress();
        cinemaDTO.eMail = cinema.geteMail();
        cinemaDTO.id = cinema.getId();
        cinemaDTO.name = cinema.getName();
        cinemaDTO.phoneNumber = cinema.getPhoneNumber();
        
        return new ResponseEntity<>(cinemaDTO, HttpStatus.OK);
    }
	
	@PostMapping(path = "/modify" , consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void modifyCinema(@RequestBody CinemaDTO cinemaDTO) throws Exception {
        Cinema cinema = new Cinema();
        cinema.setId(cinemaDTO.id);
        cinema.setAdress(cinemaDTO.adress);
        cinema.seteMail(cinemaDTO.eMail);
        cinema.setName(cinemaDTO.name);
        cinema.setPhoneNumber(cinemaDTO.phoneNumber);
        
        this.service.save(cinema);
    }
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void addCinema(@RequestBody CinemaDTO cinemaDTO) throws Exception {
        Cinema cinema = new Cinema();
        cinema.setAdress(cinemaDTO.adress);
        cinema.seteMail(cinemaDTO.eMail);
        cinema.setName(cinemaDTO.name);
        cinema.setPhoneNumber(cinemaDTO.phoneNumber);
        
        this.service.save(cinema);
    }
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteCinema(@PathVariable("id") Long id) throws Exception {
		try {
			this.service.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    }
	
	@GetMapping(
            value = "/managers/contained/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getManagersAssociatedToCinema(@PathVariable(name = "id") Long id) {
        Cinema cinema = this.service.findOne(id);
        return new ResponseEntity<>(convertListOfModelToListDTO(cinema.getManagers()), HttpStatus.OK);
    }
	
	@GetMapping(
            value = "/managers/not-contained/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getManagersNotAssociatedToCinema(@PathVariable(name = "id") Long id) {
        Cinema cinema = this.service.findOne(id);
        List<User> allUsers = this.userService.findAll();
        Set<User> notAssociatedUsers = new HashSet<>();
        
        for (User user : allUsers) {
        	if(user.getRole().equals("MENADZER")) {
        	    Set<User> managers = cinema.getManagers();
        		if(!IsCinemasManager(managers, user)) {
        			notAssociatedUsers.add(user);
        		}
        	}
        }
        
        return new ResponseEntity<>(convertListOfModelToListDTO(notAssociatedUsers), HttpStatus.OK);
    }
	
	@PostMapping(path = "/managers/add", consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void addManagerToCinema(@RequestBody CinemaManagerDTO cinemaManagerDTO) throws Exception {
        Cinema cinema = this.service.findOne(cinemaManagerDTO.cinemaId);
        User manager = this.userService.findOne(cinemaManagerDTO.managerId);
        cinema.addManager(manager);
        
        this.service.save(cinema);
    }
	
	@PostMapping(path = "/managers/remove", consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void removeManagerFromCinema(@RequestBody CinemaManagerDTO cinemaManagerDTO) throws Exception {
        Cinema cinema = this.service.findOne(cinemaManagerDTO.cinemaId);
        Set<User> cinemaManagers = cinema.getManagers();
        User user = null;
        for (User mng : cinemaManagers) {
        	if(mng.getId().equals(cinemaManagerDTO.managerId)) {
        		user = mng;
        		break;
        	}
        }
        
        if(user == null)
        	return;
        
        cinema.removeManager(user);
        
        this.service.save(cinema);
    }
	
	private boolean IsCinemasManager(Set<User> cinemasManagers, User manager){
		for (User mng : cinemasManagers) {
			if(mng.getId() == manager.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private List<UserDTO> convertListOfModelToListDTO(Set<User> inputList){
		List<UserDTO> DTOs = new ArrayList<>();

        for (User model : inputList) {
        	UserDTO DTO = new UserDTO();
            DTO.birthDate = model.getBirthDate();
            DTO.email = model.getEmail();
            DTO.firstName = model.getFirstName();
            DTO.id = model.getId();
            DTO.lastName = model.getLastName();
            DTO.password = model.getPassword();
            DTO.phone = model.getPhone();
            DTO.role = model.getRole();
            DTO.username = model.getUsername();
            DTOs.add(DTO);
        }
        
        return DTOs;
	}
}