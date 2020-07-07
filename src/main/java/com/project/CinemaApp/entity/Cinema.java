package com.project.CinemaApp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Cinema implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String adress;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String eMail;
	
	@Column
	private String manager;
	
	@OneToMany(mappedBy="cinema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CinemaHall> halls = new HashSet<>();
	
	@ManyToMany
	@JoinTable  (name = "manager_cinema",
				joinColumns = @JoinColumn(name = "cinema_id", referencedColumnName = "id"),
		        inverseJoinColumns = @JoinColumn(name = "manager_id", referencedColumnName = "id"))
	private Set<User> managers = new HashSet<>();

	
	public Set<User> getManagers() {
		return managers;
	}

	public void setManagers(Set<User> managers) {
		this.managers = managers;
	}
	
	public void addManager(User manager) {
		managers.add(manager);
		manager.getManagedCinemas().add(this);
	}
	
	public void removeManager(User manager) {
		managers.remove(manager);
		manager.getManagedCinemas().remove(this);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Set<CinemaHall> getHalls() {
		return halls;
	}

	public void setHalls(Set<CinemaHall> halls) {
		this.halls = halls;
	}

	@Override
	public String toString() {
		return "Cinema [id=" + id + 
				", name=" + name + 
				", adress=" + adress + 
				", phoneNumber=" + phoneNumber + 
				", eMail=" + eMail + 
				", manager=" + manager + "]";
	}
	
	
	
}
