package com.accenture.Farm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.Farm.entity.User;

@Repository
public interface userRepository extends CrudRepository<User, Long> {

	
	public Optional<User> findByUsername(String username);
}
