package com.example.demo.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.User;
import com.example.demo.domain.service.RestService;

@RestController
public class UserRestController {

	@Autowired
	@Qualifier("RestServiceMybatisImpl")
	RestService service;
	
	//全件取得
	@GetMapping("/rest/get")
	public List<User> getUserMany(){
		
		return service.selectMany();
	}
	
	//1件取得
	@GetMapping("/rest/get/{id:.+}")
	public User getUserOne(@PathVariable("id")String userId) {
		
		return service.selectOne(userId);
	}
	
	//1件登録
	@PostMapping("/rest/insert")
	public String postUserOne(@RequestBody User user) {
		
		boolean result = service.insert(user);
		
		String str = "";
		
		if(result == true) {
			str="{\"result\":\"ok\"}";
		}else {
			str = "{\"result\":\"error\"}";
		}
		return str;
	}
	
	//1件更新
	@PutMapping("/rest/update")
	public String putUserOne(@RequestBody User user) {
		
		boolean result = service.update(user);
		
		String str ="";
		if(result == true) {
			str="{\"result\":\"ok\"}";
		}else {
			str = "{\"result\":\"error\"}";
		}
		return str;
	}
	
	//1件削除
	@DeleteMapping("/rest/delete/{id:.+}")
	public String deleteUserOne(@PathVariable("id") String userId) {
		
		boolean result = service.delete(userId);
		String str ="";
		if(result == true) {
			str="{\"result\":\"ok\"}";
		}else {
			str = "{\"result\":\"error\"}";
		}
		return str;
	}
}
