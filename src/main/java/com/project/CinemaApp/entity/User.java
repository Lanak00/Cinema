package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

//import org.springframework.context.support.BeanDefinitionDsl.Role;
//enum Role{ADMINISTRATOR, MENADZER, KORISNIK}
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
	
	@Column
	private Boolean isActive;
	
	@ManyToMany
	@JoinTable  (name = "reserved_tickets",
    			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    			inverseJoinColumns = @JoinColumn(name = "projection_id", referencedColumnName = "id"))
	private Set<Projection> projectionsReserved = new HashSet<>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<WatchedMovies> watchedMovies = new HashSet<WatchedMovies>();
	
	@ManyToMany (mappedBy = "managers", fetch = FetchType.LAZY)
	private Set<Cinema> managedCinemas = new HashSet<>();
	
	
	public Set<Cinema> getManagedCinemas() {
		return managedCinemas;
	}

	public void setManagedCinemas(Set<Cinema> managedCinemas) {
		this.managedCinemas = managedCinemas;
	}

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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<Projection> getProjectionsReserved() {
		return projectionsReserved;
	}

	public void setProjectionsReserved(Set<Projection> projectionsReserved) {
		this.projectionsReserved = projectionsReserved;
	}

	public Set<WatchedMovies> getWatchedMovies() {
		return watchedMovies;
	}

	public void setWatchedMovies(Set<WatchedMovies> watchedMovies) {
		this.watchedMovies = watchedMovies;
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
