package com.project.CinemaApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.CinemaApp.entity.Cinema;
import com.project.CinemaApp.service.CinemaService;

@Controller
public class CinemaController {
	
	@Autowired
	private CinemaService cinemaService;
	
	@GetMapping("/cinema-form")
    public String cinemaForm(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "cinema-form.html";
    }
	
	@PostMapping("/add-cinema") 
    public String addCinema(@ModelAttribute("cinema") Cinema cinema) {
		this.cinemaService.save(cinema);
    	return "administrator.html";
    }
	
	 @GetMapping("/cinemas")
	 public String getCinemas(Model model) {
		List<Cinema> cinemas = this.cinemaService.findAll();
	    model.addAttribute("cinemas", cinemas);
	    return "cinemas.html";
	}
}
