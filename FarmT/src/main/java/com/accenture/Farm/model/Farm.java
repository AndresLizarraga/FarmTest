package com.accenture.Farm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="farm")
public class Farm {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;

	
	@OneToMany (cascade = CascadeType.ALL,
			mappedBy="farm")
	private List<Chickens> chickens;

	

	
	
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

	public List<Chickens> getChickens() {
		return chickens;
	}

	public void setChickens(List<Chickens> chickens) {
		this.chickens = chickens;
	}


	
}
