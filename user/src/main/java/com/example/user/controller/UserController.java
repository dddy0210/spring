package com.example.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.dto.UserDTO;
import com.example.user.model.UserEntity;
import com.example.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
	private final UserService service;
	
	//유저 생성 
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO dto) {
		//서비스로 보내기 위해서 DTO-> entity로 바꿈
		UserEntity entity = UserDTO.toEntity(dto);
		List<UserDTO> users = service.create(entity);
		return ResponseEntity.ok().body(users);

	}
	//모든 유저 조회 
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
	    List<UserDTO> users = service.getAllUsers();
	    return ResponseEntity.ok(users);
	}
	//이메일을 통한 한명 조회하기
	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
	    UserDTO user = service.getUserByEmail(email);
	    return ResponseEntity.ok(user);
	}
	//id를 통해 수정하기
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody UserDTO dto) {
	    UserEntity entity = UserDTO.toEntity(dto);
	    List<UserDTO> updatedUser = service.updateUser(entity);

	    return ResponseEntity.ok(updatedUser);
	    }
	@DeleteMapping("/{id}")
	public ResponseEntity<?>deleteUser(@PathVariable int id) {
	    boolean isDeleted = service.deleteUser(id);
	    if (isDeleted) {
	        return ResponseEntity.ok("User deleted successfully");
	    } else {
	        return ResponseEntity.status(404).body("User not found with id " + id);
	    }
	}
	
}