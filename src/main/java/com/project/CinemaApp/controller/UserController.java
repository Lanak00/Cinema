package com.project.CinemaApp.controller;

import java.util.ArrayList;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.PostMapping;
import com.project.CinemaApp.entity.User;

//import models.IdentityModel;

//import models.UserModel;
//import com.project.CinemaApp.service.UserService;
//import org.springframework.context.support.BeanDefinitionDsl.Role;

@Controller
public class UserController {
 
	private static ArrayList<User> users = new ArrayList<User>();
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
	    //this.userService.save(user);
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
				break;
			}
		}
        
        if(loggedUser == null){
        	return "login.html";
        }
        
        return "start-page.html";
    }
	
	@GetMapping("/my-profile")
    public String viewProfile (Model model) {
        model.addAttribute("user", loggedUser);
        return "my-profile";
    }
	
	@GetMapping("/logout")
    public String logout() {
		loggedUser=null;
       // model.addAttribute("user", loggedUser);
        return "index.html";
    }
	
}
