package com.project.CinemaApp.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class WatchedMovies implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	WatchedMoviesKey id;
	
	@ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
	private User user;
 
    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    private Movie movie;
 
    @Column
    private int rating;

	public WatchedMoviesKey getId() {
		return id;
	}

	public void setId(WatchedMoviesKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "MovieRating [id=" + id + 
			   ", user=" + user + 
			   ", movie=" + movie + 
			   ", rating=" + rating + "]";
	}
     
    
}
