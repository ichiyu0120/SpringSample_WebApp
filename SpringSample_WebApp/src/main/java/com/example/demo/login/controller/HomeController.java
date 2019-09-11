package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.SignupForm;
import com.example.demo.domain.model.User;
import com.example.demo.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	//結婚ステータスのラジオボタン用変数
	private Map<String,String> radioMarriage;
	
	//ラジオボタンの初期化（登録時と同じ）
	private Map<String,String> initRadioMarriage(){
		Map<String,String> radio = new LinkedHashMap<>();
		
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
	}
	
	
	@GetMapping("/home")
	public String getHome(Model model) {
		
	model.addAttribute("contents", "login/home :: home_contents");
		
		return "login/homeLayout";
	}
	
	@PostMapping("/logout")
	public String postLogout() {
		
		return "redirect:/login";
	}
	
	@GetMapping("/userList")
	public String getUserList(Model model) {
		
		model.addAttribute("contents","login/userList :: userList_contents");
		
		List<User> userList = userService.selectMany();
		model.addAttribute("userList",userList);
		
		int count = userService.count();
		model.addAttribute("userListCount", count);
		
		return "login/homeLayout";
	}
	
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model,
									@PathVariable ("id")String userId) {
		System.out.println("userId="+userId);
		model.addAttribute("contents","login/userDetail :: userDetail_contents");
		
		radioMarriage = initRadioMarriage();
		model.addAttribute("radioMarriage",radioMarriage);
		
		if(userId != null && userId.length() > 0) {
			User user = userService.selectOne(userId);
			
			form.setUserId(user.getUserId());
			form.setUserName(user.getUserName());
			form.setBirthday(user.getBirthday());
			form.setAge(user.getAge());
			form.setMarriage(user.isMarriage());
			model.addAttribute("signupForm", form);
		}
		
		return "login/homeLayout";
	}
	
	@PostMapping(value="/userDetail",params = "update")
	public String postUserDeatailUpdate(@ModelAttribute SignupForm form, Model model) {
		System.out.println("更新ボタンの処理");
		
		User user = new User();
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		
		try {
			
		
		//更新処理 
		boolean result = userService.updateOne(user);
		if(result == true) {
			model.addAttribute("result","更新成功");
		}else {
			model.addAttribute("result","更新失敗");
		}
		
		}catch(DataAccessException e) {
			model.addAttribute("result","更新失敗（トランザクションテスト）");
		}
		
		return getUserList(model);
	}
	
	@PostMapping(value="/userDetail",params="delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form,Model model) {
		System.out.println("削除ボタンの処理");
		
		boolean result = userService.deleteOne(form.getUserId());
		if(result == true) {
			model.addAttribute("result","削除成功");
		}else {
			model.addAttribute("result","削除失敗");
		}
		
		return getUserList(model);
	}
	
	//ユーザー一覧のCSV出力用処理
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model){
		
		//ユーザーを全件取得してCSVをサーバーに保存する
		userService.userCsvOut();
		
		byte[] bytes = null;
		try {
			//サーバーに保存されているsample.csvファイルをbyteで取得する
			bytes = userService.getFile("sample.csv");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");
		
		//sample.csvを戻す
		return new ResponseEntity<>(bytes,header,HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	public String getAdmin(Model model) {
		model.addAttribute("contents","login/admin :: admin_contents");
		
		return "login/homeLayout";
	}
}
