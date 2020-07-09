package com.project.CinemaApp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class WatchedMoviesKey implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column (name = "user_id")
	private Long userId;
	
	@Column (name = "movie_id")
	private Long movieId;

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
        if (!(obj instanceof WatchedMoviesKey)) {
            return false;
        }
        WatchedMoviesKey key = (WatchedMoviesKey) obj;
        return userId == key.userId &&
               movieId == key.movieId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(userId, movieId);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
}
