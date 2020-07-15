package com.project.CinemaApp.entity.dto;

import org.springframework.web.multipart.MultipartFile;

public class MovieDTO {
	public Long id;
	public String title;
	public String description;
	public String genre;
	public String duration;
	public String averageRating;
	public MultipartFile image;
	public String imageName;
}
