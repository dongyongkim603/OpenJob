package com.JohnHaney.OpenJob.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.JohnHaney.OpenJob.LoadDictionaries;
import com.JohnHaney.OpenJob.models.Job;
import com.JohnHaney.OpenJob.models.Photo;
import com.JohnHaney.OpenJob.models.User;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.PhotoServices;

@Controller
public class JobController {

	@Autowired
	PhotoServices photoServices;

	@Autowired
	JobServices jobServices;

	/**
	 * gets the create post new job page passes new job object to model
	 * 
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
	 * The form method for creating a new job posting which allows users to add
	 * images, set job name, job description, and price.
	 * 
	 * @param imageFile the MultipartFile which contains the image bytes
	 * @param newJob    the job container which contains the new job object
	 * @param bind      will bind the results
	 * @param session   contains the logged in users data "currentUser"
	 * @return
	 */
	@PostMapping("/postNewJob")
	public ModelAndView postNewJob(@RequestParam("imageFile") MultipartFile imageFile,
			@Valid @ModelAttribute("newJob") Job newJob, BindingResult bind, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			newJob.setCreationDate(LocalDate.now());
			newJob.setUser((User) session.getAttribute("currentUser"));//
			jobServices.save(newJob);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
		}

		if (!imageFile.isEmpty()) {
			// create new photo from uploaded MultipartFile
			Photo photo = new Photo();
			String fileName = imageFile.getOriginalFilename();
			photo.setFileName(fileName);
			photo.setPath("/photos/");// this line will override photos with same name change this hard coded line to
										// be dynamic
			photo.setJob(newJob);

			try {
				photoServices.saveImage(imageFile, photo);
				modelAndView.addObject("photo", photo);
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView.setViewName("error");
			}
		}

		modelAndView.addObject("job", newJob);
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}

	@GetMapping("/postJob/edit/{id}")
	public String editJob(Model model, @PathVariable("id") Long jobId) {
		Job editJob = jobServices.findById(jobId);
		model.addAttribute("newJob", editJob);
		return "editJob";
	}
	
	@GetMapping("/deleteJob/{id}")
	public String deleteJob(Model model, @PathVariable("id") Long jobId, HttpSession session) {
		System.out.println("deleting job...");
		Job job = jobServices.findById(jobId);
		
		//try to delete all photos related to job
		try {
			List<Photo> photo = photoServices.findByJob(job);
			for (Photo p : photo) {
				photoServices.deleteById(p.getPhotoId());
			}
		} catch (Exception e) {
			e.getMessage();
		}
		
		//try to delete all jobs from user
		
		
		jobServices.deleteById(jobId);
		System.out.println("Job was Deleted... ");
		return "index";
	}
}
