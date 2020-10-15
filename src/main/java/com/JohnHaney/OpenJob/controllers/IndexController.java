package com.JohnHaney.OpenJob.controllers;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.JohnHaney.OpenJob.models.User;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String showHomePage(Model modelUser) {
		return "index";//template names
	}
	
	@GetMapping("/login")
	public String showLogin() {
		String encoded = new BCryptPasswordEncoder().encode("admin");//used to create an in memory user and password
		System.out.println("bcrypt encoded password: "+ encoded);
		return "login";//template names
	}
	
	@RequestMapping("/accessdenied")
	public String accessDenied() {
		return "accessdenied";//template names
	}
	
}
