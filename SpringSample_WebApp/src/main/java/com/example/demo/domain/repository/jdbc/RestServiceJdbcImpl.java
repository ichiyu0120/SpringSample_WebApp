package com.example.demo.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserDao;
import com.example.demo.domain.service.RestService;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

	@Autowired
	@Qualifier("UserDaoJdbcImp1")
	UserDao dao;
	
	//1件登録
	@Override
	public boolean insert(User user) {
		
		int result = dao.insertOne(user);
		if(result == 0) {
			return false;
		}else {
			return true;
		}
	}

	//1件検索
	@Override
	public User selectOne(String userId) {
		
		return dao.selectOne(userId);
	}

	//全件検索
	@Override
	public List<User> selectMany() {
		
		return dao.selectMany();
	}

	//1件更新
	@Override
	public boolean update(User user) {
		
		int result = dao.updateOne(user);
		
		if(result == 0) {
			return false;
		}else {
			return true;
		}
	}

	//1件削除
	@Override
	public boolean delete(String userId) {
		
		int result = dao.deleteOne(userId);
		
		if(result == 0) {
			return false;
		}else {
			return true;
		}
	}

}
