package com.example.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.repository.UserDao;

@Service
public class UserService {
	
	@Autowired
	UserDao dao;

}
