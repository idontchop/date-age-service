package com.idontchop.dateageservice.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class UserProfileDto {
	

	private String username;			// Not editable
	
	private Date birthday;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBirthday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(birthday);
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getName() {
		return username;
	}
	
	

}
