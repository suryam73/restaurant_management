package com.code.Restaurant_springs.repositroy;

import com.code.Restaurant_springs.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	List<Product> findAllByCategoryId(Long categoryId);

	List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);

}
