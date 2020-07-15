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
	private String averageRating;
	
	@Column 
	private String imageFileName;
	
	
	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	@OneToMany (mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WatchedMovies> usersWatched = new HashSet<WatchedMovies>() ;
	
	@OneToMany (mappedBy = "movie")
	private Set<Projection> projections = new HashSet<Projection>();

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

	public String getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}

	public Set<WatchedMovies> getUsersWatched() {
		return usersWatched;
	}

	public void setUsersWatched(Set<WatchedMovies> usersWatched) {
		this.usersWatched = usersWatched;
	}

	public Set<Projection> getProjections() {
		return projections;
	}

	public void setProjections(Set<Projection> projections) {
		this.projections = projections;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", description=" + description + ", genre=" + genre
				+ ", duration=" + duration + ", averageRating=" + averageRating + ", usersWatched=" + usersWatched
				+ ", projections=" + projections + "]";
	}

	
	



	
	
	
}
