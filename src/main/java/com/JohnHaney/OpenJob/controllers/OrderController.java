package com.JohnHaney.OpenJob.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.JohnHaney.OpenJob.models.ReviewDTO;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.UserServices;

@Controller
public class OrderController {

	@Autowired
	JobServices jobServices;

	@Autowired
	UserServices userServices;

	@PostMapping("/{jobId}/order")
	public String addJobToOrder(Model model, @PathVariable("jobId") Long jobId,
			@Valid @ModelAttribute("review") ReviewDTO review, HttpSession session) {
		String returnPage = jobId.toString();
				
		return "redirect:/" + returnPage;
	}
}
