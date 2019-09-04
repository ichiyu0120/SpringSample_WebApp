package com.example.demo.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	
	private String userId;
	private String password;
	private String userName;
	private Date birthday;
	private int age;
	private boolean marriage;
	private String role;
	
}
