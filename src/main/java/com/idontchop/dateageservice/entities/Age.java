package com.idontchop.dateageservice.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * User.find({
  birthday: {
    $gte: moment(date).subtract(30, 'years'),
    $lte: moment(date).subtract(25, 'years')
  }
})
 */
@Document
public class Age {
	
	public Age() {}
	
	private String name;
	
	@NotNull
	private Date birthday;
	
	public Age (String name) {
		this.name = name;
	}
	
	public Age (String name, Date birthday) {
		this.birthday = birthday;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Transient
	public int getAge() {
		LocalDate bd8 =
				Instant.ofEpochMilli(birthday.getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		return Period.between(bd8, LocalDate.now()).getYears();
	}

}
