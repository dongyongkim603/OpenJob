package com.JohnHaney.OpenJob.controllers;

import java.time.LocalDate;
import java.util.List;
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

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.Job;
import com.JohnHaney.OpenJob.models.Photo;
import com.JohnHaney.OpenJob.models.User;
import com.JohnHaney.OpenJob.security.SecurityUtils;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.PhotoServices;

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
	public String showHomePage(HttpSession session, Model jobPostModel) {
		try {			User user = userRepo.findByUsername(SecurityUtils.getUser()).get();
			if (user.getEmail() != null)
				session.setAttribute("currentUser", user);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
		List<Job> jobList = jobServices.findAll();
//		List<Photo> photos = photoServices.findByJob(jobList.get(0));
		List<Photo> photos = photoServices.findAll();
		System.out.println("photo Id: " + photos.get(0).getFileName());
		jobPostModel.addAttribute("jobList", jobList);
		jobPostModel.addAttribute("photos", photos);
		return "index";// template names
	}

	/**
	 * sends the user to the login page and also creates an in memory admin id and password
	 * @return the URL of the login page
	 */
	@GetMapping("/login")
	public String showLogin() {
		String encoded = new BCryptPasswordEncoder().encode("admin");// used to create an in memory user and password
		System.out.println("bcrypt encoded password: " + encoded);
//		User logedInUser = SecurityUtils.getUser();
		return "login";// template names
	}

	/**
	 * The get handler method that will send users to the access denied page
	 * @param session 
	 * @return
	 */
	@RequestMapping("/accessdenied")
	public String accessDenied(HttpSession session) {
//		try {
//			User user = userRepo.findByUsername(SecurityUtils.getUser()).get();
//			if (user.getEmail() != null)
//				session.setAttribute("currentUser", user);
//		} catch (NoSuchElementException e) {
//			e.getMessage();
//		}
		return "accessdenied";// template names
	}

	/**
	 * gets the create post new job page passes new job object to model
	 * @param jobModel the job object container 
	 * @return the postjob page
	 */
	@GetMapping("/postjob")
	public String postJob(Model jobModel) {
		Job newJob = new Job();
		jobModel.addAttribute("newJob", newJob);
		return "postjob";
	}

	/**
	 * The form method for creating a new job posting which allows users to add images, set job name, job description, and price.
	 * @param imageFile the MultipartFile which contains the image bytes
	 * @param newJob the job container which contains the new job object
	 * @param bind will bind the results
	 * @param session contains the logged in users data "currentUser"
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
	 * stand alone method for uploading images
	 * @param imageFile the MultipartFile that contains the image(s) data
	 * @return will return the URL of the home page
	 */
	@PostMapping("/uploadImage")
	public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
		String returnValue = "/";

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
