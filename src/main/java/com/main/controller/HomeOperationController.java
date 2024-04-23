package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeOperationController {

	@GetMapping("/")
	public String showHomePageHelper()
	{
		//return LVN
		return "welcome";
	}
}
