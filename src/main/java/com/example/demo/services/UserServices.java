package com.example.demo.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.Userdto;
import com.example.demo.model.User;

public interface UserServices {

	ResponseEntity<String> save(Userdto userdto);

	ResponseEntity<String> findById(Long userId);

	ResponseEntity<String> deleteById(Long userId);

	List<User> alllist();

	ResponseEntity<Object> sendmail(String email);

	ResponseEntity<String> verify(Long otp, String email, Userdto userdto);

	ResponseEntity<String> update(Userdto userdto, Long userId);

	ResponseEntity<String> login(String email, String password);

}
