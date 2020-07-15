package com.project.CinemaApp.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public User findOne(Long id) {
    	User user = this.userRepository.getOne(id);
        return user;
    }
    
    public User findUser(String username, String password) {
    	User user = this.userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
 
    public List<User> findAll() {
    	List<User> users = this.userRepository.findAll();
        return users;
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public void delete(Long id){
		User obj = this.userRepository.getOne(id);
		if(obj != null) {
			this.userRepository.delete(obj);
		}
	}
    
    public List<User> findByUsername(String username){
    	return this.userRepository.findByUsername(username);
    }
    
    public List<User> findByPassword(String password){
    	return this.userRepository.findByPassword(password);
    }
   
}