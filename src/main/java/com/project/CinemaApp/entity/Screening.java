package com.project.CinemaApp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Screening implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;
	
	@Column
	private Date dateAndTime;
	
	@Column
	private double price;
	
	@ManyToOne
	@JoinColumn(name = "cinema_id", nullable = false)
	private Cinema cinema;

	@OneToOne(mappedBy = "screening")
    private Projection projection;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Screening [Id=" + Id + 
			   ", movie=" + movie + 
			   ", dateAndTime=" + dateAndTime + 
			   ", price=" + price + "]";
	}
	
	
	
	

}
