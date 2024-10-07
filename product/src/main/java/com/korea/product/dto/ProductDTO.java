package com.korea.product.dto;

import com.korea.product.model.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor // builder패턴을 사용하려면 있어야한다.
@Data

public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private double price;
	
	//Entity -> DTO
	public ProductDTO(ProductEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
	}
	
	//DTO -> Entity
	public static ProductEntity toEntity(ProductDTO dto) {
		return ProductEntity.builder() 
				.id(dto.getId())
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.build();
	}
}