package com.example.demo.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImp1 implements UserDao{
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int count() throws DataAccessException {
		
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
		return count;
	}

	@Override
	public int insertOne(User user) throws DataAccessException {
	
		int rowNumber = jdbc.update("INSERT INTO m_user("
				+ "user_id,"
				+ "password,"
				+ "user_name,"
				+ "birthday,"
				+ "age,"
				+ "marriage,"
				+ "role) "
				+ "VALUES(?,?,?,?,?,?,?)",
				user.getUserId(),
				user.getPassword(),
				user.getUserName(),
				user.getBirthday(),
				user.getAge(),
				user.isMarriage(),
				user.getRole());
		return rowNumber;
	}

	@Override
	public User selectOne(String userId) throws DataAccessException {
		
		Map<String,Object> map = jdbc.queryForMap("SELECT * FROM m_user "
				+ "WHERE user_id=?",userId);
		
		User user = new User();
		
		user.setUserId((String)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setBirthday((Date)map.get("birthday"));
		user.setAge((Integer)map.get("age"));
		user.setMarriage((Boolean)map.get("marriage"));
		user.setRole((String)map.get("role"));
		
		
		return user;
	}

	@Override
	public List<User> selectMany() throws DataAccessException {
		
		//M_userテーブルのデータを全件取得
		List<Map<String,Object>> getList = jdbc.queryForList("SELECT * FROM m_user");
		
		//結果返却用の変数
		List<User> userList = new ArrayList<>();
		
		//拡張for文で返却用Listに格納
		for(Map<String,Object> map : getList) {
			User user = new User();
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarriage((Boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));
			
			userList.add(user);
		}
		
		return userList;
	}

	@Override
	public int updateOne(User user) throws DataAccessException {
		
		int rowNumber =jdbc.update("UPDATE m_user SET "
				+ "password=?, "
				+ "user_name=?, "
				+ "birthday=?, "
				+ "age=?, "
				+ "marriage=? "
				+ "WHERE user_id=?",
				user.getPassword(),
				user.getUserName(),
				user.getBirthday(),
				user.getAge(),
				user.isMarriage(),
				user.getUserId());
		return rowNumber;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
