package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false)
	private String username;
	
	@Column (nullable = false)
	private String password;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column 
	private String email;
	
	@Column
	private String phone;
	
	@Column
	private String birthDate;
	
	@Column
	private String role;

	@ManyToMany 
	@JoinTable  (name = "movies_watched",
				joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
	private Set<Movie> watchedMovies = new HashSet<>();
	
	@ManyToMany
	@JoinTable  (name = "tickets_reserved",
    			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    			inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
	private Set<Movie> reservedTickets = new HashSet<>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MovieRating> ratings = new HashSet<MovieRating>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Movie> getWatchedMovies() {
		return watchedMovies;
	}

	public void setWatchedMovies(Set<Movie> watchedMovies) {
		this.watchedMovies = watchedMovies;
	}

	public Set<Movie> getReservedTickets() {
		return reservedTickets;
	}

	public void setReservedTickets(Set<Movie> reservedTickets) {
		this.reservedTickets = reservedTickets;
	}

	public Set<MovieRating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<MovieRating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "User [id=" + id + 
				", username=" + username + 
				", password=" + password + 
				", firstName=" + firstName + 
				", lastName=" + lastName + 
				", email=" + email + 
				", phone=" + phone + 
				", birthDate=" + birthDate
				+ ", role=" + role + "]";
	}
	
	
}
