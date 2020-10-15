package com.JohnHaney.OpenJob.controllers;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.User;
import com.JohnHaney.OpenJob.security.SecurityUtils;

@Controller
public class IndexController {

	@Autowired
	private UserRepoIF userRepo;

	@RequestMapping("/")
	public String showHomePage(HttpSession session) {
		try {
			User user = userRepo.findByUsername(SecurityUtils.getUser()).get();
			if (user.getEmail() != null)
				session.setAttribute("currentUser", user);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
		return "index";// template names
	}

	@GetMapping("/login")
	public String showLogin() {
		String encoded = new BCryptPasswordEncoder().encode("admin");// used to create an in memory user and password
		System.out.println("bcrypt encoded password: " + encoded);

//		User logedInUser = SecurityUtils.getUser();
		return "login";// template names
	}

	@RequestMapping("/accessdenied")
	public String accessDenied() {
		return "accessdenied";// template names
	}

}
