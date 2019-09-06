package com.example.demo.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.User;

@Repository("UserDaoJdbcImp4")
public class UserDaoJdbcImp4 extends UserDaoJdbcImp1{
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public List<User> selectMany(){
		
		String sql = "SELECT * FROM m_user";
		
		UserResultSetExtractor extractor = new UserResultSetExtractor();
		
		return jdbc.query(sql,extractor);
	}
}
