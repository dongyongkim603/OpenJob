package com.JohnHaney.OpenJob.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String listUsers(Model modelUser) {
		return "/Home";
	}
	
}
