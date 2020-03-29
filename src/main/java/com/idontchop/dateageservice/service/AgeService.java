package com.idontchop.dateageservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.idontchop.dateageservice.entities.Age;
import com.idontchop.dateageservice.repositories.AgeRepository;

@Service
public class AgeService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	AgeRepository ageRepository;
	
	public Age getAge (String name) {
		return ageRepository.findByName(name).orElseThrow();
	}
	
	public Age newAge (String name, Date birthday) {
		Age newAge = new Age(name,birthday);
		
		newAge = ageRepository.save(newAge);
		
		return newAge;		
	}

}
