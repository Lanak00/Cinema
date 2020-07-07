package com.project.CinemaApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CinemaApp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsernameAndPassword(String username, String password);
	
	List<User> findByUsername(String username);
	
	List<User> findByPassword(String password);
}
