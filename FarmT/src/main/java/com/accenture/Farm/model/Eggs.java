package com.accenture.Farm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Eggs")
public class Eggs {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="chickens_id")
	private Chickens chickens;

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

	public Chickens getChickens() {
		return chickens;
	}

	public void setChickens(Chickens chickens) {
		this.chickens = chickens;
	}

	
}
