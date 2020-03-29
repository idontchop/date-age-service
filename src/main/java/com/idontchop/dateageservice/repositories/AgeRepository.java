package com.idontchop.dateageservice.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.idontchop.dateageservice.entities.Age;

public interface AgeRepository extends MongoRepository<Age,String> {
	
	public Optional<Age> findByName(String name);

}
