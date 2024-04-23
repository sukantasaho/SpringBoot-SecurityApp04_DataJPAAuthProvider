package com.main.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.entity.UserDetailsInfo;
import com.main.service.IUserDetailsService;

@Controller
@RequestMapping("/user")
public class UserOperationController {

	@Autowired
	private IUserDetailsService service;
	
	@GetMapping(value = "/registerForm")
	public String showUserRegistrationForm(@ModelAttribute("userInfo") UserDetailsInfo userInfo)
	{
		//return LVN
		return "user_register_form";
	}
	@PostMapping("/register")
	public String registerUser(Map<String,Object> map, @ModelAttribute("userInfo") UserDetailsInfo userInfo)
	{
		  String result = service.registerUser(userInfo);
		  
		  map.put("resultMsg", result);
		  return "user_registered_success";
	}
	@GetMapping("/login")
	public String showCustomLoginForm()
	{
		return "custom_login";
	}
}
