package com.code.Restaurant_springs.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.Restaurant_springs.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByNameContaining(String title);

}
