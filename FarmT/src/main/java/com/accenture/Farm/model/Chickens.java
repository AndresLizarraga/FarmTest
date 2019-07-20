package com.accenture.Farm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Chickens")
public class Chickens {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	
	@OneToMany (cascade = CascadeType.ALL,
			mappedBy="chickens")
	private List<Eggs> eggs = new ArrayList <Eggs>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Farm getFarm() {
		return farm;
	}


	public void setFarm(Farm farm) {
		this.farm = farm;
	}


	public List<Eggs> getEggs() {
		return eggs;
	}


	public void setEggs(List<Eggs> eggs) {
		this.eggs = eggs;
	}
	
	
	
}
