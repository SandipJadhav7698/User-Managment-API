package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.Userdto;
import com.example.demo.model.User;
import com.example.demo.services.UserServices;

@RestController
public class UserController {
@Autowired
UserServices userServices;

@PostMapping("/save")
public ResponseEntity<String> save(@RequestBody Userdto userdto){
	return userServices.save(userdto);
}

@GetMapping("/user/{userId}")
public ResponseEntity<String> find(@PathVariable("userId") Long userId){
	return userServices.findById(userId);
}
@DeleteMapping("/{userId}")
public ResponseEntity<String> delete(@PathVariable("userId")Long userId){
	return userServices.deleteById(userId);
}

@GetMapping("/list")
List<User> list(){
	return userServices.alllist();
}
@PostMapping("/user/{email}")
public ResponseEntity<Object> sendmail(@PathVariable("email") String email){
	return userServices.sendmail(email);
}

@PostMapping("/verify")
public ResponseEntity<String> verify(Long otp,String email,@RequestBody Userdto userdto){
	return userServices.verify(otp,email,userdto);
}

@PutMapping("/update/{userId}")
public ResponseEntity<String> update(@RequestBody Userdto userdto,@PathVariable("userId")Long userId){
	return userServices.update(userdto,userId);
}
@PostMapping("/login")
public ResponseEntity<String> login(String email,String password){
	return userServices.login(email,password);
}
}
