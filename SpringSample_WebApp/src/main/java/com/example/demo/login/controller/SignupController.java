package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.GroupOrder;
import com.example.demo.domain.model.SignupForm;
import com.example.demo.domain.model.User;
import com.example.demo.domain.service.UserService;

@Controller
public class SignupController {
	
	@Autowired
	private UserService userService;
	

	private Map<String,String> radioMarriage;
	
	private Map<String,String> initRadioMarrige(){
		
		Map<String,String> radio = new LinkedHashMap<>();
		
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
	}
	
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form,Model model) {
		
		radioMarriage = initRadioMarrige();
		
		model.addAttribute("radioMarriage", radioMarriage);
		
		return "login/signup";
	}
	
	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,BindingResult bindingResult,Model model) {
		
		//入力チェックに引っかかった場合、ユーザー登録画面に戻る
		if(bindingResult.hasErrors()) {
			return getSignUp(form, model);
		}
		System.out.println(form);
		
		//insert用
		User user = new User();
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		user.setRole("ROLE_GENERAL");
		
		boolean result = userService.insert(user);
		
		if(result==true) {
			System.out.println("insert 成功");
		}else {
			System.out.println("insert 失敗");
		}
		
		return "redirect:/login";
	}
}
