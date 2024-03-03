package com.code.Restaurant_springs.services.athu;

import com.code.Restaurant_springs.dto.SignupRequest;
import com.code.Restaurant_springs.dto.UserDto;

public interface AuthService {
	
	UserDto createCustomer(SignupRequest signupRequest);
	
	boolean hasCustomerWithEmail(String email);
}
