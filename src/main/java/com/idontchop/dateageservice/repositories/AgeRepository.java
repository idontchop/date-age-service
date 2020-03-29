package com.idontchop.dateageservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.idontchop.dateageservice.entities.Age;

public interface AgeRepository extends MongoRepository<Age,String> {
	
	public Optional<Age> findByName(String name);
	
	@Query (" { 'name' : { '$in' : ?0 },"
			+ " 'birthday': {"
			+ "    $gte: new Date(),"
			+ "    $lte: new Date()} }")
	public List<Age> findAllByAgeRang(List<String> potentials, int minAge, int maxAge);

}
