package com.code.Restaurant_springs.dto;

import com.code.Restaurant_springs.enums.UserRole;

import lombok.Data;
@Data
public class AuthenticationResponse {

	private String jwt;
	
	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	private UserRole userRole;
	
	private long userId;
	
	
	
}
