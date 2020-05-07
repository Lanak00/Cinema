package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Projection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "screening_id", referencedColumnName = "id")
	private Screening screening;
	
	@Column
	private int ticketsReserved;
	
	@ManyToMany (mappedBy = "projections" )
	private Set<CinemaHall> halls = new HashSet<>();
	
	public int getTicketsReserved() {
		return ticketsReserved;
	}

	public void setTicketsReserved(int ticketsReserved) {
		this.ticketsReserved = ticketsReserved;
	}
	
	@Override
	public String toString() {
		return "Projection [ticketsReserved=" + ticketsReserved + ']';
	
	}

	
	


	 
}
