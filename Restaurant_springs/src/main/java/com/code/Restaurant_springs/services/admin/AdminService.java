package com.code.Restaurant_springs.services.admin;

import java.io.IOException;
import java.util.List;

import com.code.Restaurant_springs.dto.CategoryDto;
import com.code.Restaurant_springs.dto.ProductDto;
import com.code.Restaurant_springs.dto.ReservationDto;

public interface AdminService {

	CategoryDto postCategory(CategoryDto categoryDto);

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getAllCategoriesByTitle(String title);

	ProductDto postProduct(Long categoryId, ProductDto productDto) throws IOException;

	List<ProductDto> getAllProductsByCategory(Long categoryId);

	List<ProductDto> getProductsByCategoryAndTitle(Long categoryId,String title);

	void deleteProduct(Long productId);

	ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

	ProductDto getProductById(Long productId);

	List<ReservationDto> getReservations();

	ReservationDto changeReservationStatus(Long reservationId, String status);



}
