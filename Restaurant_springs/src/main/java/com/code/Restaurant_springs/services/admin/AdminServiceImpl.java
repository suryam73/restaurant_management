package com.code.Restaurant_springs.services.admin;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Optional; 
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.code.Restaurant_springs.dto.CategoryDto;
import com.code.Restaurant_springs.dto.ProductDto;
import com.code.Restaurant_springs.dto.ReservationDto;
import com.code.Restaurant_springs.entity.Category;
import com.code.Restaurant_springs.entity.Product;
import com.code.Restaurant_springs.entity.Reservation;
import com.code.Restaurant_springs.enums.ReservationStatus;
import com.code.Restaurant_springs.repositroy.CategoryRepository;
import com.code.Restaurant_springs.repositroy.ProductRepository;
import com.code.Restaurant_springs.repositroy.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final CategoryRepository categoryRepository;
	public AdminServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ReservationRepository reservationRepository) {
        this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.reservationRepository = reservationRepository;
    }
	
	
	private final ProductRepository productRepository;
	private final ReservationRepository reservationRepository;

	
	@Override
	public CategoryDto postCategory(CategoryDto categoryDto) {
		Category category=new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		try {
			category.setImg(categoryDto.getImg().getBytes());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Category createdCategory = categoryRepository.save(category);
		CategoryDto createdCategoryDto=new CategoryDto();
		createdCategoryDto.setId(createdCategory.getId());
		
		return createdCategoryDto ;
	}
	
	@Override
	public List<CategoryDto> getAllCategories() {
		
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}
	
	@Override
	public List<CategoryDto> getAllCategoriesByTitle(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());

	}
	
	//product operation
	
	@Override
	public ProductDto postProduct(Long categoryId, ProductDto productDto) throws IOException {
	    Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
	    if (optionalCategory.isPresent()) {
	        Product product = new Product();
	        BeanUtils.copyProperties(productDto, product);
	        product.setImg(productDto.getImg().getBytes());
	        product.setCategory(optionalCategory.get());
	        Product createdProduct = productRepository.save(product);
	        ProductDto createdProductDto = new ProductDto();
	        createdProductDto.setId(createdProduct.getId());
	        return createdProductDto;
	    } 
	        // Handle the case where the category is not found
	        return null;
	    
	}

	@Override
	public List<ProductDto> getAllProductsByCategory(Long categoryId) {
		
		return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title) {

		return productRepository.findAllByCategoryIdAndNameContaining(categoryId,title).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public void deleteProduct(Long productId) {
		Optional<Product> optionalProduct=productRepository.findById(productId);
		if(optionalProduct.isPresent()) {
			productRepository.deleteById(productId);
			}
		throw new IllegalArgumentException("Product with id"+productId+"not found");
	}

	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isPresent()) {
		Product product = optionalProduct.get();
		product.setName (productDto.getName());
		product.setDescription (productDto.getDescription());
		product.setPrice (productDto.getPrice());
		if (productDto.getImg() != null) {
		product.setImg (productDto.getImg().getBytes());
		}
		Product updatedProduct = productRepository.save (product);
		ProductDto updatedProductDto = new ProductDto();
		updatedProductDto.setId(updatedProduct.getId());
		return updatedProductDto;
		}
		return null;
	}

	@Override
	public ProductDto getProductById(Long productId) {
		Optional<Product> optionalProduct=productRepository.findById(productId);
		
		return optionalProduct.map(Product::getProductDto).orElse(null);
	}

	@Override
	public List<ReservationDto> getReservations() {
	
		return reservationRepository.findAll().stream().map(Reservation::getReservationDto).collect(Collectors.toList());

	}

	@Override
	public ReservationDto changeReservationStatus(Long reservationId, String status) {
		Optional<Reservation> optionalReservation=reservationRepository.findById(reservationId);
		if(optionalReservation.isPresent()) {
			Reservation existingReservation=optionalReservation.get();
			if(Objects.equals(status, "Approve")) {
				existingReservation.setReservationStatus(ReservationStatus.APPROVED);
			}else{
				existingReservation.setReservationStatus(ReservationStatus.DISAPPROVED);
			}
			Reservation updateReservation=reservationRepository.save(existingReservation);
			ReservationDto updateReservationDto =new ReservationDto();
			updateReservationDto.setId(updateReservation.getId());
			return updateReservationDto;
		}
		return null;
	}

	
	
	
}
