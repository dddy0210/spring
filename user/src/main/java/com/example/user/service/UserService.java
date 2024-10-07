package com.example.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.user.dto.UserDTO;
import com.example.user.model.UserEntity;
import com.example.user.persistence.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	//사용자 생성
	//컨트롤러로부터 이름과 이메일이 담겨있는 Entity를 넘겨받는다.
	//db에 추가한 후 추가가 잘 됐는지 조회를 한 데이터를 컨트롤러로 넘겨야 한다.
	private final UserRepository repository;
	
	public List<UserDTO> create(UserEntity entity) {
		repository.save(entity); //데이터베이스에 저장
		
        //List<UserEntity>-> List<UserDTO>로 변환
		return repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
	}
	//모든 유저 조회
	public List<UserDTO> getAllUsers(){
	    return repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
	}
	//이메일을 통한 한명 조회하기
	public UserDTO getUserByEmail(String email) {
	    UserEntity entity = repository.findByEmail(email);
	    return new UserDTO(entity);
	}
	//updateUser()
	public List<UserDTO> updateUser(UserEntity entity) {
	//Optional로 id통해 db에서 사용자를 찾는다.
		Optional<UserEntity> original =repository.findById(entity.getId()); 
	//사용자가 존재할 경우 업데이트로 로직 실행
		original.ifPresent(userEntity ->{
			//이름과 이메일을 객체에 setting한다. 
			 userEntity.setName(entity.getName());
		     userEntity.setEmail(entity.getEmail());
		     
		     //업데이트 된 사용자 정보 저장
		     repository.save(userEntity);
		});
		return getAllUsers();
	}
	public boolean deleteUser(int id) {
	    
	    Optional<UserEntity> exisit = repository.findById(id);

	    if (exisit.isPresent()) {
	       
	        repository.deleteById(id);
	        return true;  
	    } else {
	        return false; 
	}
	}
}


