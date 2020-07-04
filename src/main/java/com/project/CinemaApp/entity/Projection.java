package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Projection implements Serializable {

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
	
	@Column
	private int ticketsReserved;
	
	@ManyToMany (mappedBy = "projections" )
	private Set<CinemaHall> halls = new HashSet<>();
	
	@ManyToMany (mappedBy = "projectionsReserved")
	private Set<User> usersReserved = new HashSet<>();
	
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

	public int getTicketsReserved() {
		return ticketsReserved;
	}

	public void setTicketsReserved(int ticketsReserved) {
		this.ticketsReserved = ticketsReserved;
	}

	public Set<CinemaHall> getHalls() {
		return halls;
	}

	public void setHalls(Set<CinemaHall> halls) {
		this.halls = halls;
	}

	public Set<User> getUsersReserved() {
		return usersReserved;
	}

	public void setUsersReserved(Set<User> usersReserved) {
		this.usersReserved = usersReserved;
	}

	@Override
	public String toString() {
		return "Projection [Id=" + Id + ", "
				+ "movie=" + movie + ", "
				+ "dateAndTime=" + dateAndTime + ","
				+ " price=" + price
				+ ", ticketsReserved=" + ticketsReserved + ","
				+ " halls=" + halls + "]";
	}
	
	
	
	
	

	
	


	 
}
