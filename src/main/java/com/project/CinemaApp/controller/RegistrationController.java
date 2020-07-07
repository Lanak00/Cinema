package com.project.CinemaApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.entity.dto.UserDTO;
import com.project.CinemaApp.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class RegistrationController {

	private final UserService service;
	
    @Autowired
    public RegistrationController(UserService service) {
        this.service = service;
    }
    
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain") 
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) throws Exception {
    	
    	List<User> usersUsername = this.service.findByUsername(userDTO.username);
    	if(usersUsername.size() >= 1)
    		return new ResponseEntity<>("USERNAME", HttpStatus.NOT_ACCEPTABLE);
    	
    	List<User> usersPassword = this.service.findByPassword(userDTO.password);
    	if(usersPassword.size() >= 1)
    		return new ResponseEntity<>("PASSWORD", HttpStatus.NOT_ACCEPTABLE);
    	
        User user = new User();
        user.setBirthDate(userDTO.birthDate);
        user.setEmail(userDTO.email);
        user.setFirstName(userDTO.firstName);
        user.setId(userDTO.id);
        user.setIsActive(true);
        user.setLastName(userDTO.lastName);
        user.setPassword(userDTO.password);
        user.setPhone(userDTO.phone);
        user.setRole("KORISNIK");
        user.setUsername(userDTO.username);
        
        this.service.save(user);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}