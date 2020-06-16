package com.project.CinemaApp.controller;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.entity.User;


@Controller
public class UserController {
	
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
	public static User loggedUser = null;
	
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
		users.add(user);
    	return "login.html";
    }
	
	@GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login.html";
    }
	
	@PostMapping("/login-check")
    public String loginCheck(@ModelAttribute("user") User user) {

        loggedUser = null;
        for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(user.getUsername())
			   && users.get(i).getPassword().equals(user.getPassword())) {
				loggedUser = users.get(i);
				loggedUser.setRole("KORISNIK");
				break;
			}
		}
        if(loggedUser == null){
        	return "login.html";
        }  
        if(loggedUser.getRole().equals("ADMINISTRATOR") ) {
        	return "administrator.html";
        } else {
        return "start-page.html";
        }
    }
	
	@GetMapping("/my-profile")
    public String viewProfile (Model model) {
        model.addAttribute("user", loggedUser);
        return "my-profile";
    }
	
	@GetMapping("/logout")
    public String logout() {
		loggedUser=null;
        return "index.html";
    }
	
	@GetMapping("/cinema-form")
    public String cinemaForm(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "cinema-form.html";
    }
	
	@PostMapping("/add-cinema") 
    public String addCinema(@ModelAttribute("cinema") Cinema cinema) {
		cinemas.add(cinema);
    	return "administrator.html";
    }
	
	 @GetMapping("/cinemas")
	 public String getCinemas(Model model) {
	    model.addAttribute("cinemas", cinemas);
	    return "cinemas.html";
	}
	
}
