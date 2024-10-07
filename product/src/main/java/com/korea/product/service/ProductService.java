package com.korea.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.korea.product.dto.ProductDTO;
import com.korea.product.model.ProductEntity;
import com.korea.product.persistence.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository repository;
	
	public ProductDTO addProduct(ProductDTO productDTO){
		ProductEntity entity = ProductDTO.toEntity(productDTO);	
		return new ProductDTO(repository.save(entity));
	}
	public List<ProductDTO> getFilteredProducts(Double minPrice,String name) {
		
		List<ProductEntity> products = repository.findAll();
		
		//가격 필터림(최소금액이 주어진 경우)
		 if(minPrice != null) {
			 products = products.stream()
					 .filter(product -> product.getPrice() >= minPrice)
					 .collect(Collectors.toList());
		 }
		//이름 필터링(name이 넘어왔을 경우)
		 if(name != null && name.isEmpty()) {
			 products = products.stream()
					 	.filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
					 	.collect(Collectors.toList());
			 
		 }
		 return products.stream().map(ProductDTO:: new ).collect(Collectors.toList());
	}
	
	public List<ProductDTO> updateProduct(ProductEntity entity){
		
		Optional<ProductEntity> optional = repository.findById(entity.getId());
		if(optional != null) {
		optional.ifPresent(ProductEntity -> {
			ProductEntity.setName(entity.getName());
			ProductEntity.setDescription(entity.getDescription());
			ProductEntity.setPrice(entity.getPrice());
			
			repository.save(ProductEntity);
		});
		}else {
			return null;
		}
		return repository.findAll().stream().map(ProductDTO :: new).collect(Collectors.toList());
		
	}
	//상품수정
	public ProductDTO updateProduct(long id, ProductDTO dto) {
	    //db에서 id에 해당하는 데이터가 있는지 조회
        Optional<ProductEntity> original = repository.findById(id);
        
        //있으면 매개변수로 넘어온 dto에 있는 내용으로 기존의 내용을 갱신
        if(original.isPresent()) {
        	ProductEntity entity = original.get();
        	entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            repository.save(entity);
        }
        return null;
}
	public boolean deleteProduct(long id) {
		Optional<ProductEntity> original = repository.findById(id);
		
		if(original.isPresent()) {
			repository.deleteById(id);
            return true;
		}
		return false; 
}
}
