package com.example.demo.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.dto.Userdto;
import com.example.demo.model.User;
import com.example.demo.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {
@Autowired
UserRepository userRepository;

//save the User
@Override
public ResponseEntity<String> save(Userdto userdto) {
	User user=new User();
	user.setUserName(userdto.getUserName());
	user.setEmail(userdto.getEmail());
	user.setPassword(userdto.getPassword());
	user.setPhoneNo(userdto.getPhoneNo());
	//user.setDate(new Date());
	userRepository.save(user);
	return new ResponseEntity<>("USER ARE SAVED SUCESSFULLY.... ",HttpStatus.OK); 
}


//Find By Id 
@Override
public ResponseEntity<String> findById(Long userId) {
Optional<User> user=userRepository.findById(userId);
if(user.isPresent()) {
	return new ResponseEntity<>("USER ARE PRESNT....",HttpStatus.OK);
}else {
	return new ResponseEntity<>("USER NOT PRESNT....",HttpStatus.OK);
}
}


//delete By Id
@Override
public ResponseEntity<String> deleteById(Long userId) {
userRepository.deleteById(userId);
return new ResponseEntity<>("USER DELETE SUCESSFULLY....",HttpStatus.OK);
}


//All User List
@Override
public List<User> alllist() {
List<User>list=userRepository.findAll();
return list;
}


//Send the Email Otp
@Autowired
private JavaMailSender javaMailSender;

@Value("${spring.mail.username}")
private String send;

@Override
public ResponseEntity<Object> sendmail(String email) {
	Optional<User> email1=userRepository.findByemail(email);
	//for use of manually Sending
	//String to="Enter Your Email";
	int max = 10000000;
	int min = 99999999;
	Long a = (long) (Math.random() * (max - min + 1) + min);   
	System.out.println(a); 
	if(email1.isPresent()) {
try {
	User user=userRepository.getByEmail(email);
	SimpleMailMessage mailMessage=new SimpleMailMessage();
	mailMessage.setFrom(send);
	mailMessage.setTo(email);
	mailMessage.setSubject("verification Email ");
	mailMessage.setText("OTP is "+a);
	user.setOtp(a);
	user.setDate(new Date());
	javaMailSender.send(mailMessage);
	userRepository.save(user);
	
	return new ResponseEntity<>("Email Send Successfully...",HttpStatus.OK);
} catch (Exception e) {
e.printStackTrace();
}	}
	return new ResponseEntity<>("Email NOT Send Successfully...",HttpStatus.OK);

}
}
