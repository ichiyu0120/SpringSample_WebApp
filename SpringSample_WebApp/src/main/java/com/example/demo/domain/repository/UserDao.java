package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.domain.model.User;

public interface UserDao {
	
	//Userテーブル件数取得
	public int count() throws DataAccessException;
	
	//Userテーブル1件insert
	public int insertOne(User user) throws DataAccessException;
	
	//Userテーブル1件取得
	public User selectOne(String userId) throws DataAccessException;
	
	//Userテーブル全件取得
	public List<User> selectMany() throws DataAccessException;
	
	//Userテーブル1件更新
	public int updateOne(User user) throws DataAccessException;
	
	//Userテーブル1件削除
	public int deleteOne(String userId) throws DataAccessException;
	
	//SQL取得結果をサーバーにCSVで保存する
	public void userCsvOut() throws DataAccessException;
}
