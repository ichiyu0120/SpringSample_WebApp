package com.example.demo.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.User;

@Repository("UserDaoJdbcImp3")
public class UserDaoJdbcImp3 extends UserDaoJdbcImp1{
	
	@Autowired
	private JdbcTemplate jdbc;
	
	//1件取得
	@Override
	public User selectOne(String userId) {
		
		String sql = "SELECT * FROM m_user WHERE user_id=?";
		
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		
		return jdbc.queryForObject(sql,rowMapper,userId);
		
	}
	
	//全件取得
	@Override
	public List<User> selectMany(){
		
		String sql = "SELECT * FROM m_user";
		
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		
		return jdbc.query(sql,rowMapper);
	}
}
