package com.code.Restaurant_springs.services.athu.jwt; // Corrected package name to use lowercase 'auth'

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
    UserDetailsService userDetailsService();
}
