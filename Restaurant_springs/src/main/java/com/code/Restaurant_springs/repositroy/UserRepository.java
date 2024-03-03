package com.code.Restaurant_springs.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.Restaurant_springs.entity.User; // Change import to use User entity instead of UserDto
import com.code.Restaurant_springs.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Change JpaRepository<UserDto, Long> to JpaRepository<User, Long>
	
	Optional<User> findFirstByEmail(String email);

	User findByUserRole(UserRole admin);

}
