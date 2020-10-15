package com.JohnHaney.OpenJob.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.JohnHaney.OpenJob.models.User;
import com.JohnHaney.OpenJob.services.UserServices;

@Controller
public class ProfileController {
	
	@Autowired
	UserServices userService;

//	@GetMapping("/profile")
//	public String profilePage(Model userModel, Principal principal) {
//		String un = principal.getName();
//		
//		userModel.addAllAttributes(attributeValues);
//	}
	
	@GetMapping("/register")
	public String adduser(Model modelUsers) {
		// object of Users
		User newUser = new User();
		
		
		// container for new user
		modelUsers.addAttribute("newUser", newUser);
		
		return "register";
	}
	
	@PostMapping("/registerUser")
	public String saveUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult bind) {
	//newUser.toString();
			userService.save(newUser);
		return "redirect:/";
	}
}
