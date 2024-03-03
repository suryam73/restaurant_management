package com.code.Restaurant_springs.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.code.Restaurant_springs.dto.CategoryDto;
import com.code.Restaurant_springs.dto.ProductDto;
import com.code.Restaurant_springs.dto.ReservationDto;
import com.code.Restaurant_springs.entity.Category;
import com.code.Restaurant_springs.entity.Product;
import com.code.Restaurant_springs.entity.Reservation;
import com.code.Restaurant_springs.entity.User;
import com.code.Restaurant_springs.enums.ReservationStatus;
import com.code.Restaurant_springs.repositroy.CategoryRepository;
import com.code.Restaurant_springs.repositroy.ProductRepository;
import com.code.Restaurant_springs.repositroy.ReservationRepository;
import com.code.Restaurant_springs.repositroy.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl  implements CustomerService{

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	public CustomerServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository,ReservationRepository reservationRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.reservationRepository=reservationRepository;
		this.userRepository = userRepository;
    }
	
	@Override
	public List<CategoryDto> getAllCategories() {
		
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}
	
	@Override
	public List<CategoryDto> getCategoriesByName(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());

	}

	@Override
	public List<ProductDto> getProductsByCategory(Long categoryId) {
		
		return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title) {
		return productRepository.findAllByCategoryIdAndNameContaining(categoryId,title).stream().map(Product::getProductDto).collect(Collectors.toList());

	}

	@Override
	public ReservationDto postReservation(ReservationDto reservationDto) {
		Optional<User> optionalUser=userRepository.findById(reservationDto.getCustomerId());
		if(optionalUser.isPresent()) {
		Reservation reservation =new Reservation();
		reservation.setTableType(reservationDto.getTableType());
		reservation.setDateTime(reservationDto.getDateTime());
		reservation.setDescription(reservationDto.getDescription());
		reservation.setUser(optionalUser.get());
		reservation.setReservationStatus(ReservationStatus.PENDING);
		Reservation postedReservation =reservationRepository.save(reservation);
		ReservationDto postedReservationDto=new ReservationDto();
		postedReservationDto.setId(postedReservation.getId());
		return postedReservationDto;
		}
		return null;
	}

	@Override
	public List<ReservationDto> getReservationByUser(Long customerId) {
		
		return reservationRepository.findAllByUserId(customerId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}
	
	

	
	
}
