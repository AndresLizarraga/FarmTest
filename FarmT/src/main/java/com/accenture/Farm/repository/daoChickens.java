package com.accenture.Farm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.accenture.Farm.model.Chickens;
import com.accenture.Farm.model.Farm;

public interface daoChickens extends CrudRepository<Chickens, Long> {

	
	public List<Chickens> findByFarm(Farm farm);
}
