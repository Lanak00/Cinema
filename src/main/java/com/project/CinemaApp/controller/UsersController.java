package com.project.CinemaApp.controller;

import java.util.ArrayList;
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
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.entity.dto.CinemaDTO;
import com.project.CinemaApp.entity.dto.UserDTO;
import com.project.CinemaApp.service.CinemaService;
import com.project.CinemaApp.service.UserService;

@RestController
@RequestMapping(value = "/api/managers")
public class UsersController {

	private final UserService service;
	private final CinemaService cinemaService;
	
    @Autowired
    public UsersController(UserService service, CinemaService cinemaService) {
        this.service = service;
        this.cinemaService = cinemaService;
    }
    
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> list = this.service.findAll();
        List<UserDTO> DTOs = new ArrayList<>();

        for (User model : list) {
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
        
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
	

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) 
    public void addManager(@RequestBody UserDTO userDTO) throws Exception {
        User user = new User();
        user.setBirthDate(userDTO.birthDate);
        user.setEmail(userDTO.email);
        user.setFirstName(userDTO.firstName);
        user.setId(userDTO.id);
        user.setIsActive(true);
        user.setLastName(userDTO.lastName);
        user.setPassword(userDTO.password);
        user.setPhone(userDTO.phone);
        user.setRole("MENADZER");
        user.setUsername(userDTO.username);
        
        this.service.save(user);
    }
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") Long id) throws Exception {
		try {
			User user = this.service.findOne(id);
			Set<Cinema> cinemas = user.getManagedCinemas();
			
			List<Cinema> cinemasCopy = new ArrayList<Cinema>();
			cinemasCopy.addAll(cinemas);
			
			for(Cinema c : cinemasCopy) {
				c.removeManager(user);
				this.cinemaService.save(c);
			}
			
			this.service.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
	@GetMapping(value = "/getAssociatedCinemas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CinemaDTO>> getManagerCinemas(@PathVariable("id") Long id) {
        User manager = this.service.findOne(id);
        Set<Cinema> managedCinemas =  manager.getManagedCinemas();
        List<CinemaDTO> DTOs = new ArrayList<>();
        
        for (Cinema model : managedCinemas) {
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
	
	
}