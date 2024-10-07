package com.korea.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korea.product.dto.ProductDTO;
import com.korea.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController // 이 클래스가 REST API의 컨트롤러임을 나타냄
@RequestMapping("/products") // "/products"로 요청을 받음
@RequiredArgsConstructor // 자동으로 생성자를 만들어줌
public class ProductController {

    private final ProductService productService; // ProductService를 주입받음

    // 상품 추가하기
    @PostMapping // POST 요청을 처리함
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO dto) {
   

        // 저장된 상품을 DTO로 변환
        ProductDTO createProduct = productService.addProduct(dto);
        return ResponseEntity.ok().body(createProduct);
    }
    
    @GetMapping
    public ResponseEntity<?> getFilteredProducts(
    		  @RequestParam(value = "minPrice", required = false) Double minPrice, 
    	        @RequestParam(value = "name", required = false) String name) {
        // 필터링된 상품 목록 가져오기
        List<ProductDTO> filteredProducts = productService.getFilteredProducts(minPrice, name);
        return ResponseEntity.ok().body(filteredProducts); // 결과를 응답으로 반환
    }
    
    // 상품 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductDTO dto) {
        ProductDTO u_dto = productService.updateProduct(id, dto);
        if (u_dto!= null) {
            return ResponseEntity.ok(u_dto);
        }
        return ResponseEntity.notFound().build();
    }
    //상품 삭제하기 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id, @RequestBody ProductDTO dto){
    	boolean result =productService.deleteProduct(id);
    	 if (result) {
             return ResponseEntity.ok("삭제 잘됨");
         }
         return ResponseEntity.badRequest().body("삭제 안됨");
     }
 }


