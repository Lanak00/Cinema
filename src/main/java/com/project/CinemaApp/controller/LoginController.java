package com.project.CinemaApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.entity.dto.LoginDTO;
import com.project.CinemaApp.entity.dto.UserDTO;
import com.project.CinemaApp.service.LoginService;

@RestController
@RequestMapping(value = "/api")
public class LoginController {

	private final LoginService service;
	
    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }
    
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain") 
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) throws Exception {
        User user = this.service.login(loginDTO.username, loginDTO.password);
        
        if(user == null)
        	return new ResponseEntity<>("PROBLEM", HttpStatus.UNAUTHORIZED);
        
        return new ResponseEntity<>(user.getRole(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/logout")
    public void logout() {
        this.service.logout();
    }
    
    @GetMapping(path = "/getLoggedUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getLoggedUser() {
    	User model = this.service.getLoggedUser();
    	
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
        
    	return new ResponseEntity<>(DTO, HttpStatus.OK);
    }
    
    
}