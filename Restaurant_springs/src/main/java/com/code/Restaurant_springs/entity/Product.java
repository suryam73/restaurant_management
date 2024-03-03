package com.code.Restaurant_springs.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.code.Restaurant_springs.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long id;
	
    private String name;
    
    private String price;
	
	private String description;
	
	@Lob
	@Column(columnDefinition ="longblob")
	
	private byte[] img;
	
	@ManyToOne(fetch= FetchType.LAZY, optional=false)
	@JoinColumn(name="category_id",nullable=false)
	@OnDelete(action =OnDeleteAction.CASCADE)
	@JsonIgnore
	private Category category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public ProductDto getProductDto() {
		ProductDto productDto =new ProductDto();
		productDto.setId(id);
		productDto.setName(name);
		productDto.setPrice(price);
		productDto.setDescription(description);
		productDto.setReturnedImg(img);
		productDto.setCategoryId(category.getId());
		productDto.setCategoryName(category.getName());
		return productDto;
	}
	
}
