package com.example.demo.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserDao;

@Service
public class UserService {
	
	@Autowired
	UserDao dao;
	
	//insert用メソッド
	public boolean insert(User user) {
		
		//insert実行
		int rowNumber = dao.insertOne(user);
		
		//判定用変数
		boolean result = false;
		
		//判定
		if(0 < rowNumber) {
			result=true;
		}
		return result;
	}
	
	//count用メソッド
	public int count() {
		return dao.count();
	}
	
	//全件取得用メソッド
	public List<User> selectMany(){
		
		return dao.selectMany();
	}
	
	//1件取得用メソッド
	public User selectOne(String userId) {
		//selectOne実行
		return dao.selectOne(userId);
	}

}
