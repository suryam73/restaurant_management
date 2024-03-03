package com.code.Restaurant_springs.services.athu;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.Restaurant_springs.dto.SignupRequest;
import com.code.Restaurant_springs.dto.UserDto;
import com.code.Restaurant_springs.entity.User;
import com.code.Restaurant_springs.enums.UserRole;
import com.code.Restaurant_springs.repositroy.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount =userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount==null) {
			User newAdminAccount =new User();
			newAdminAccount.setName("Admin");
			newAdminAccount.setEmail("admin@test.com");
			newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
			newAdminAccount.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdminAccount);
			System.out.println("Admin Account Created Successfully");
		}
		
	}
	

	
	@Override
	public UserDto createCustomer(SignupRequest signupRequest) {
		
		User user = new User();

		user.setName(signupRequest.getName());

		user.setEmail(signupRequest.getEmail());

		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

		user.setUserRole(UserRole.CUSTOMER);

		User createdUser=userRepository.save(user);

		UserDto userDto = new UserDto();

		userDto.setId(createdUser.getId());

		return userDto;
	}
		
		
	@Override
	
	public boolean hasCustomerWithEmail (String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}


	}
	

