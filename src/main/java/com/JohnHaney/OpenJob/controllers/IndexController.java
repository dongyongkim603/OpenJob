package com.JohnHaney.OpenJob.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.JohnHaney.OpenJob.DAO.PhotoRepoIF;
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
	private PhotoRepoIF photoRepo;

	@Autowired
	JobServices jobServices;

	@Autowired
	PhotoServices photoServices;
	
	@Autowired
	UserServices userServices;

	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/")
	public String showHomePage(HttpSession session, Model jobPostModel) {
		try {
			User user = userRepo.findByUsername(SecurityUtils.getUser()).get();
			if (user.getEmail() != null)
				session.setAttribute("currentUser", user);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
		List<Job> jobList = jobServices.findAll();
//		List<Photo> photos = photoServices.findByJob(jobList.get(0));
		List<Photo> photos = photoServices.findAll();
		jobPostModel.addAttribute("jobList", jobList);
		jobPostModel.addAttribute("photos", photos);
		System.out.println("there are " + jobList.size() + " jobs");
		return "index";
		// findPagination(1, 1, jobPostModel); template names
	}

	/**
	 * stand alone method for uploading images
	 * 
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

	@GetMapping("/page/{pageNumber}/{pageSize}")
	public String findPagination(@PathVariable(value = "pageNumber") int pageNumber,
			@PathVariable(value = "pageSize") int pageSize, Model model) {
		Page<Job> page = jobServices.findPaginated(pageNumber, pageSize);
		List<Job> jobList = page.getContent();

		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalJobs", page.getNumberOfElements());
		model.addAttribute("jobList", jobList);
		return "index";
	}
}
