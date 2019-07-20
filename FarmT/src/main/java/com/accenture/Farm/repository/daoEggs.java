package com.accenture.Farm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.accenture.Farm.model.Chickens;
import com.accenture.Farm.model.Eggs;

public interface daoEggs extends CrudRepository<Eggs, Long> {


	public List<Eggs> findByChickens(Chickens chicken);
}
