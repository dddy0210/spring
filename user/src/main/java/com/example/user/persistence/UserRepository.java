package com.example.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByEmail(String email);
}
