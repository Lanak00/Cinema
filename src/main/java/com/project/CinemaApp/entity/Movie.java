package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


@Entity
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String title;
	
	@Column 
	private String description;
	
	@Column 
	private String genre;
	
	@Column 
	private String duration;
	  
	@Column 
	private Double averageRating;

	@ManyToMany (mappedBy = "watchedMovies" )
	private Set<User> watched = new HashSet<>();
	
	@ManyToMany (mappedBy = "reservedTickets")
	private Set<User> reserved = new HashSet<>();
	
	@OneToMany (mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MovieRating> ratings = new HashSet<MovieRating>() ;
	
	@OneToMany (mappedBy = "movie")
	private Set<Screening> screenings = new HashSet<Screening>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Set<User> getWatched() {
		return watched;
	}

	public void setWatched(Set<User> watched) {
		this.watched = watched;
	}

	public Set<User> getReserved() {
		return reserved;
	}

	public void setReserved(Set<User> reserved) {
		this.reserved = reserved;
	}

	public Set<MovieRating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<MovieRating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + 
			   ", title=" + title + 
			   ", description=" + description + 
			   ", genre=" + genre + 
			   ", duration=" + duration + 
			   ", averageRating=" + averageRating + "]";
	}
	
	
	
	
}
