package com.code.Restaurant_springs.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.Restaurant_springs.dto.AuthenticationRequest;
import com.code.Restaurant_springs.dto.AuthenticationResponse;
import com.code.Restaurant_springs.dto.SignupRequest;
import com.code.Restaurant_springs.dto.UserDto;
import com.code.Restaurant_springs.entity.User;
import com.code.Restaurant_springs.repositroy.UserRepository;
import com.code.Restaurant_springs.services.athu.AuthService;
import com.code.Restaurant_springs.services.athu.jwt.UserService;
import com.code.Restaurant_springs.utils.JWTUtil;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	private final AuthenticationManager authenticationManager; 
	
	private final UserService userService;
	
	private final JWTUtil jwtUtil;
	
	private final UserRepository userRepository;
	
	
	public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, JWTUtil jwtUtil) {
        this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
    }
	


	@PostMapping("/signup")

	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
		if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
			return new ResponseEntity<> ( "Customer already exist with this Email Id", HttpStatus.NOT_ACCEPTABLE);
	    UserDto createdCustomerDto = authService.createCustomer(signupRequest);
		if (createdCustomerDto == null) 
			return new ResponseEntity<> ( "Customer not created, Come again later", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);

	}
	
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws
	BadCredentialsException,
	DisabledException,
	UsernameNotFoundException{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect user and password");
		}
		final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser =userRepository.findFirstByEmail(userDetails.getUsername());

		final String jwt =jwtUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse=new AuthenticationResponse();
		if(optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserId(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
		}
		
		
		return authenticationResponse;
	}
}
