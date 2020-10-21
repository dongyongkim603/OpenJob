package com.JohnHaney.OpenJob.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.JohnHaney.OpenJob.DAO.JobRepoIF;
import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.Job;
import com.JohnHaney.OpenJob.models.Photo;
import com.JohnHaney.OpenJob.models.User;
import com.JohnHaney.OpenJob.security.SecurityUtils;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.PhotoServices;
import com.JohnHaney.OpenJob.services.UserServices;

@Controller
public class IndexController {

	@Autowired
	private UserRepoIF userRepo;

	@Autowired
	JobServices jobServices;
	
	@Autowired
	PhotoServices photoServices;

	/**
	 * 
	 * @param session
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String showLogin() {
		String encoded = new BCryptPasswordEncoder().encode("admin");// used to create an in memory user and password
		System.out.println("bcrypt encoded password: " + encoded);

//		User logedInUser = SecurityUtils.getUser();
		return "login";// template names
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/accessdenied")
	public String accessDenied(HttpSession session) {
		try {
			User user = userRepo.findByUsername(SecurityUtils.getUser()).get();
			if (user.getEmail() != null)
				session.setAttribute("currentUser", user);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
		return "accessdenied";// template names
	}

	/**
	 * 
	 * @param jobModel
	 * @return
	 */
	@GetMapping("/postjob")
	public String postJob(Model jobModel, HttpSession session) {
		Job newJob = new Job();
		jobModel.addAttribute("newJob", newJob);
		return "postjob";
	}

	/**
	 * 
	 * @param imageFile
	 * @param newJob
	 * @param bind
	 * @return
	 */
	@PostMapping("/postNewJob")
	public ModelAndView postNewJob(@RequestParam("imageFile") MultipartFile imageFile,
			@Valid @ModelAttribute("newJob") Job newJob, BindingResult bind, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			//if the Job does not exist save
			if (!jobServices.existsByName(newJob.getJobName())) {
				newJob.setCreationDate(LocalDate.now());
				newJob.setUser((User)session.getAttribute("currentUser"));//
				jobServices.save(newJob);
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
			return modelAndView;
		}
		//create new photo from uploaded MultipartFile
		Photo photo = new Photo();
		String fileName = imageFile.getOriginalFilename();
		photo.setFileName(fileName);
		photo.setPath("/photos/");// this line will override photos with same name change this hard coded line to
									// be dynamic
		photo.setJob(newJob);
		modelAndView.setViewName("success");
		
		try {
			photoServices.saveImage(imageFile, photo);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
			return modelAndView;
		}
		
		modelAndView.addObject("photo", photo);
		modelAndView.addObject("job", newJob);
		return modelAndView;
	}

	/**
	 * 
	 * @param imageFile
	 * @return
	 */
	@PostMapping("/uploadImage")
	public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
		String returnValue = "start";

		Photo photo = new Photo();
		photo.setFileName(imageFile.getOriginalFilename());
		photo.setPath("/photos/");
		try {
			photoServices.saveImage(imageFile, photo);
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = "error";
		}
		return returnValue;
	}

}
