package com.code.Restaurant_springs.services.athu.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.code.Restaurant_springs.repositroy.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    
      private final UserRepository userRepository;
      public UserServiceImpl(UserRepository userRepository) {
          this.userRepository = userRepository;
      }
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findFirstByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            }
        };
    }
}
