package com.project.CinemaApp.entity.dto;

import java.util.List;

public class ProjectionAddDTO {
	public Long id;
	public Long movieId;
	public List<Long> halls;
	public String dateAndTime;
	public double price;
}
