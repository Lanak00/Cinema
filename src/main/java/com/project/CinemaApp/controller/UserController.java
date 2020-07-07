package com.project.CinemaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.CinemaApp.entity.User;
import com.project.CinemaApp.service.UserService;


@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	/*private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<Cinema> cinemas = new ArrayList<Cinema>();*/
	public static User loggedUser = null;
	
	/*public UserController() {
		User admin = new User();
		admin.setFirstName("Marko");
		admin.setLastName("Markovic");
		admin.setUsername("admiin");
		admin.setPassword("password123");
		admin.setEmail("marko@gmail.com");
		admin.setRole("ADMINISTRATOR");
		admin.setBirthDate("11.8.1995.");
		admin.setId((long)3);
		admin.setPhone("066589753");
		this.userService.save(admin);
	}*/
	
	@GetMapping("/")
	public String welcome() {
		return "index.html";
	}

	@GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration.html";
    }
		 
	@PostMapping("/save-user") 
    public String saveUser(@ModelAttribute("user") User user) {
		user.setRole("KORISNIK");
		this.userService.save(user);
    	return "login.html";
    }
	
	@GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login.html";
    }
	
	@PostMapping("/login-check")
    public String loginCheck(@ModelAttribute("user") User user) {

        loggedUser = this.userService.findUser(user.getUsername(), user.getPassword());
        if(loggedUser == null){
        	return "login.html";
        } 
        loggedUser.setIsActive(true);
        if(loggedUser.getRole().equals("ADMINISTRATOR")) {
        	return "administrator.html";
        } else {
        	return "start-page.html";
        }
    }
	
	@GetMapping("/my-profile")
    public String viewProfile (Model model) {
        model.addAttribute("user", loggedUser);
        return "my-profile.html";
    }
	
	@GetMapping("/logout")
    public String logout() {
		loggedUser=null;
        return "index.html";
    }
	
}
