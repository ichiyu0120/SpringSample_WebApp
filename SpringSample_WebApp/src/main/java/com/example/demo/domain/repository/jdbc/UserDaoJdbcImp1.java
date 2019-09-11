package com.example.demo.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserDao;

@Repository("UserDaoJdbcImp1")
public class UserDaoJdbcImp1 implements UserDao{
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public int count() throws DataAccessException {
		
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
		return count;
	}

	@Override
	public int insertOne(User user) throws DataAccessException {
	
		//パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		
		int rowNumber = jdbc.update("INSERT INTO m_user("
				+ "user_id,"
				+ "password,"
				+ "user_name,"
				+ "birthday,"
				+ "age,"
				+ "marriage,"
				+ "role,"
				+ "enable) "
				+ "VALUES(?,?,?,?,?,?,?,'true')",
				user.getUserId(),
				password,
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
		
		//パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		
		int rowNumber =jdbc.update("UPDATE m_user SET "
				+ "password=?, "
				+ "user_name=?, "
				+ "birthday=?, "
				+ "age=?, "
				+ "marriage=? "
				+ "WHERE user_id=?",
				password,
				user.getUserName(),
				user.getBirthday(),
				user.getAge(),
				user.isMarriage(),
				user.getUserId());
//		トランザクション確認のため、わざと例外をthrowする
//		if(0 < rowNumber) {
//			throw new DataAccessException("トランザクションテスト") {
//				
//			};
//		}
		return rowNumber;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		
		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id=?",userId);
		
		return rowNumber;
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		
		String sql = "SELECT * FROM m_user";
		
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		jdbc.query(sql,handler);
	}

}
