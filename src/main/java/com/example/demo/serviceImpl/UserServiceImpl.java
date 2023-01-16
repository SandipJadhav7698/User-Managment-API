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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.dto.Userdto;
import com.example.demo.model.User;
import com.example.demo.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {
@Autowired
UserRepository userRepository;

@Autowired
BCryptPasswordEncoder bEncoder;

//save the User
@Override
public ResponseEntity<String> save(Userdto userdto) {
	User user=new User();
	user.setUserName(userdto.getUserName());
	user.setEmail(userdto.getEmail());
	//Password encryption 
	user.setPassword(bEncoder.encode(userdto.getPassword()));
	//Password without encryption
	//user.setPassword(userdto.getPassword());
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
	//String to="sandip76980@gmail.com";
	int max = 10000000;
	int min = 99999999;
	Long a = (long) (Math.random() * (max - min + 1) + min);   
	System.out.println(a); 
	if(email1.isPresent()) {
try {
	User user1=userRepository.getByEmail(email);
	SimpleMailMessage mailMessage=new SimpleMailMessage();
	mailMessage.setFrom(send);
	mailMessage.setTo(email);
	mailMessage.setSubject("verification Email ");
	mailMessage.setText("OTP is "+a);
	user1.setOtp(a);
	user1.setDate(new Date());
	javaMailSender.send(mailMessage);
	userRepository.save(user1);
	
	return new ResponseEntity<>("Email Send Successfully...",HttpStatus.OK);
} catch (Exception e) {
e.printStackTrace();
}	}
	return new ResponseEntity<>("Email NOT Send Successfully...",HttpStatus.OK);

}

//otp verification
@Override
public ResponseEntity<String> verify(Long otp,String email,Userdto userdto) {
	Optional<User> email1=userRepository.findByemail(userdto.getEmail());
	Optional<User> otp1=userRepository.findByOtp(userdto.getOtp());
	long currentTimeInMillis = System.currentTimeMillis();
	long expire=otp1.get().getDate().getTime()+1 * 60 * 1000;
	System.out.println(currentTimeInMillis);
	if(email1.isPresent() && otp1.isPresent()) {
		if(currentTimeInMillis<=expire)
		{		return new ResponseEntity<>("OTP IS VERIFIED SUCESSFULLY...",HttpStatus.OK);
		
		}else {
			return new ResponseEntity<>("OTP IS NOT VALID...",HttpStatus.OK);

		}
	}
	return new ResponseEntity<>("OTP IS NOT VERIFIED...",HttpStatus.OK);
}

//update the user Information
@Override
public ResponseEntity<String> update(Userdto userdto, Long userId) {
Optional<User> user1=userRepository.findById(userId);
if(user1.isPresent()) {
	User user2=userRepository.getById(userId);
	user2.setUserName(userdto.getUserName());
	user2.setEmail(userdto.getEmail());
	user2.setPassword(userdto.getPassword());
	user2.setPhoneNo(userdto.getPhoneNo());
	userRepository.save(user2);
	return new ResponseEntity<>("USER ARE UPDATED SUCESSFULLY.... ",HttpStatus.OK); 

}else {
	return new ResponseEntity<>("USER ARE NOT PRESENT.... ",HttpStatus.OK); 

}

}

//Basic Login API

@Override
public ResponseEntity<String> login(String email, String password) {
Optional<User> email2=userRepository.findByemail(email);
System.out.println(email2);
Optional<User> pass=userRepository.findBypassword(password);
if(email2.isPresent() && pass.isPresent()) {
	return new ResponseEntity<>("LOGIN SUCESSFULLY.... ",HttpStatus.OK); 
}else {
	return new ResponseEntity<>("LOGIN FAILED.... ",HttpStatus.OK); 
}
 
}
}
