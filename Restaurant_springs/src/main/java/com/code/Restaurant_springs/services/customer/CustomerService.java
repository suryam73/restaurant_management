package com.code.Restaurant_springs.services.customer;

import java.util.List;

import com.code.Restaurant_springs.dto.CategoryDto;
import com.code.Restaurant_springs.dto.ProductDto;
import com.code.Restaurant_springs.dto.ReservationDto;

public interface CustomerService {

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getCategoriesByName(String title);

	List<ProductDto> getProductsByCategory(Long categoryId);

	List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title);

	ReservationDto postReservation(ReservationDto reservationDto);

	List<ReservationDto> getReservationByUser(Long customerId);



}
