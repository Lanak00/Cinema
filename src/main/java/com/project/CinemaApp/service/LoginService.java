package com.project.CinemaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.repository.CinemaRepository;
import com.project.CinemaApp.repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    
    private User loggedUser = null;
	
    public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	
	public User login(String username, String password){
		List<User> users = this.userRepository.findAll();
		for(User user : users) {
			if(user.getUsername().equals(username)){
				if(user.getPassword().equals(password)) {
					loggedUser = user;
					return user;
				}
				return null;
			}
		}
		
		return null;
	}
	
	public void logout() {
		loggedUser = null;
	}
}