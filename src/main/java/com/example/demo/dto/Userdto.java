package com.example.demo.dto;

import java.util.Date;

public class Userdto {
	private Long userId;
	private String userName;
	private String email;
	private String password;
	private String phoneNo;
	private Date date;
	private Long otp;
	
	public Long getOtp() {
		return otp;
	}
	public void setOtp(Long otp) {
		this.otp = otp;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
