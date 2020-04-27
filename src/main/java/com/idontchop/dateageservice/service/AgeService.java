package com.idontchop.dateageservice.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.idontchop.dateageservice.dtos.UserProfileDto;
import com.idontchop.dateageservice.entities.Age;
import com.idontchop.dateageservice.repositories.AgeRepository;

@Service
public class AgeService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	AgeRepository ageRepository;
	
	public List<Age> reduceByAge (List<String> potentials, int minAge, int maxAge) {
		
		Criteria c = new Criteria().andOperator(
				Criteria.where("name").in(potentials),
				Criteria.where("birthday").lte(getMinDate(minAge)),
				Criteria.where("birthday").gte(getMaxDate(maxAge)));
		Query query = new Query();
		query.addCriteria(c);				
		
		return mongoTemplate.find(query, Age.class);
		
	}
	
	private Date getMaxDate (int maxAge) {
		LocalDate date = LocalDate.now().minusYears(maxAge);
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	private Date getMinDate (int minAge) {
		LocalDate date = LocalDate.now().minusYears(minAge);
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public Age getAge (String name) {
		return ageRepository.findByName(name).orElseThrow();
	}
	
	public Age newAge (String name, Date birthday) {
		
		Age newAge = ageRepository.findByName(name).orElse(new Age(name,birthday));
		
		newAge = ageRepository.save(newAge);
		
		return newAge;		
	}
	
	public void deleteAge (String name) {
		
		Optional<Age> ageToDel = ageRepository.findByName(name);
		
		if ( ageToDel.isPresent() ) {
			ageRepository.delete(ageToDel.get());
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * For /api/profile endpoint
	 * 
	 * returns list of requested users.
	 * 
	 * @param users
	 * @return
	 */
	public List<UserProfileDto> getProfilesInList( List<String> users ) {
		
		return ageRepository.findAllByNameIn(users)
			.stream().map ( age -> {
				UserProfileDto dto = new UserProfileDto();
				dto.setBirthday(age.getBirthday());
				dto.setUsername(age.getName());
				return dto;
			})
			.collect(Collectors.toList());
	}

}
