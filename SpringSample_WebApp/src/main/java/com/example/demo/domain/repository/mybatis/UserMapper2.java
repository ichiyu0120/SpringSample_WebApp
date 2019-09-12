package com.example.demo.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.User;

@Mapper
public interface UserMapper2 {

	public boolean insert(User user);
	
	public User selectOne(String userId);
	
	public List<User> selectMany();
	
	public boolean updateOne(User user);
	
	public boolean deleteOne(String userId);
	}
