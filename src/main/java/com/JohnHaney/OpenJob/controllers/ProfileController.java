package com.JohnHaney.OpenJob.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JohnHaney.OpenJob.LoadDictionaries;
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
	public String adduser(Model modelUsers, Model modelContires) {
		// object of Users
		User newUser = new User();
		ArrayList<String> countries = new ArrayList<>();
		countries = LoadDictionaries.initiateDicationary();
		modelContires.addAttribute("countries", countries);

//		System.out.println("test");
//		for(String c: countries)
//			if(countries.isEmpty())
//				System.out.println("array is empty");
//			else
//			System.out.println(c);

		// container for new user
		modelUsers.addAttribute("newUser", newUser);

		return "register";
	}

	@PostMapping("/registerUser")
	public String saveUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult bind) {
		// newUser.toString();
		if (!userService.existsByUsername(newUser.getUsername())) {
			userService.save(newUser);			
			return "redirect:/";
		} 
		return "register";
	}
}
