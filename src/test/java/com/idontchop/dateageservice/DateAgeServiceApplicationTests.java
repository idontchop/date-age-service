package com.idontchop.dateageservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.idontchop.dateageservice.entities.Age;
import com.idontchop.dateageservice.repositories.AgeRepository;
import com.idontchop.dateageservice.service.AgeService;

@SpringBootTest
class DateAgeServiceApplicationTests {

	@Autowired
	AgeRepository ageRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	AgeService ageService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testFind () {
		
		List<String> tos = List.of("nada3", "nada");
		
		List<Age> a = ageRepository.findAllByAgeRang(tos, 0, 45);
		
		assertEquals ( 0, a.size());
		
		//assertTrue ( a.get(0).getName().equals("username"));
		
		assertEquals ( 1, ageService.reduceByAge(tos, 23, 24).size() );
	}

}
