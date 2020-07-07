
package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class CinemaHall implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int capacity;
	
	@Column
	private String hallMark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
	
	@ManyToMany 
	@JoinTable  (name = "projection_schedule",
				joinColumns = @JoinColumn(name = "hall_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "projection_id", referencedColumnName = "id"))
	private Set<Projection> projections = new HashSet<>();
	
	public Set<Projection> getProjections() {
		return projections;
	}

	public void setProjections(Set<Projection> projections) {
		this.projections = projections;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getHallMark() {
		return hallMark;
	}

	public void setHallMark(String hallMark) {
		this.hallMark = hallMark;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	@Override
	public String toString() {
		return "CinemaHall [id=" + id + 
				", capacity=" + capacity + 
				", hallMark=" + hallMark + 
				", cinema=" + cinema + "]";
	}
	
}
