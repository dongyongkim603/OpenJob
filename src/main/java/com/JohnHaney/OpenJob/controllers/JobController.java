package com.JohnHaney.OpenJob.controllers;

import java.time.LocalDate;
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

import com.JohnHaney.OpenJob.models.CartDTO;
import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.models.PhotoDTO;
import com.JohnHaney.OpenJob.models.ReviewDTO;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.services.CartServices;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.PhotoServices;
import com.JohnHaney.OpenJob.services.ReviewServices;
import com.JohnHaney.OpenJob.services.UserServices;

@Controller
public class JobController {

	@Autowired
	PhotoServices photoServices;

	@Autowired
	JobServices jobServices;

	@Autowired
	UserServices userServices;

	@Autowired
	ReviewServices reviewServices;

	@Autowired
	CartServices cartServices;

	/**
	 * gets the create post new job page passes new job object to model
	 * 
	 * @param jobModel the job object container
	 * @return the postjob page
	 */
	@GetMapping("/postjob")
	public String postJob(Model jobModel) {
		JobDTO newJob = new JobDTO();
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
			@Valid @ModelAttribute("newJob") JobDTO newJob, BindingResult bind, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			newJob.setCreationDate(LocalDate.now());
			newJob.setUser((UserDTO) session.getAttribute("currentUser"));//
			jobServices.save(newJob);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
		}

		if (!imageFile.isEmpty()) {
			// create new photo from uploaded MultipartFile
			PhotoDTO photo = new PhotoDTO();
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

	/**
	 * captures the jobId from the PathVariable and searches the database for the
	 * target job. This job is then passed to the Post handler.
	 * 
	 * @param model will store the target job retrieved from the database
	 * @param jobId the of the target job
	 * @return the editJob view
	 */
	@GetMapping("/postJob/edit/{id}")
	public String editJob(Model model, @PathVariable("id") Long jobId) {
		JobDTO editJob = jobServices.findById(jobId);
		model.addAttribute("newJob", editJob);
		return "editJob";
	}

	/**
	 * Allows the user to remove the job from the system and database. Will fetch
	 * all the data that has a foreign key enforced relationship and will update and
	 * delete those tuples as well.
	 * 
	 * @param jobId The target jobId retrieved from the PathVariable
	 * @return
	 */
	@GetMapping("/deleteJob/{jobId}")
	public String deleteJob(@PathVariable("jobId") Long jobId) {
		System.out.println("deleting job...");
		JobDTO job = jobServices.findById(jobId);

		// try to delete all photos related to job
		try {
			List<PhotoDTO> photo = photoServices.findByJob(job);
			for (PhotoDTO p : photo) {
				photoServices.deleteById(p.getPhotoId());
			}
		} catch (Exception e) {
			e.getMessage();
		}

		// update all the carts and remove all instances of the deleted job
		try {
			List<CartDTO> cartList = cartServices.findByJob(jobId);
			for (CartDTO c : cartList) {
				c.removeFromCart(job);
				c.updateTotal();
				cartServices.save(c);
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		try {
			List<ReviewDTO> reviewList = reviewServices.findByJobId(jobId);
			for (ReviewDTO r : reviewList) {
				reviewServices.deleteById(r.getReviewId());
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		// try to delete all jobs from user
		jobServices.deleteById(jobId);
		System.out.println("Job was Deleted... ");
		return "index";
	}

	/**
	 * Retrieves the jobId from the PathVarible and searches for the job in the
	 * database.
	 * 
	 * @param model stores the data from the retrieved job, the data of the job
	 *              owner, the photos of the job, and the reviews of the job
	 * @param jobId the target jobId
	 * @return the job view of the target job
	 */
	@GetMapping("/job/{id}")
	public String showJobPage(Model model, @PathVariable("id") Long jobId) {
		// try to find job in DB
		JobDTO job = new JobDTO();
		UserDTO user = new UserDTO();
		try {
			job = jobServices.findById(jobId);
			model.addAttribute("job", job);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		// try to find all the photos related to the job in the DB
		try {
			List<PhotoDTO> photos = photoServices.findByJob(job);
			if (!photos.isEmpty())
				model.addAttribute("photos", photos);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		// try to find user that posted the job
		try {
			user = userServices.findById(job.getUser().getUserId());
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		// try to find all the related reviews
		try {
			List<ReviewDTO> reviewList = reviewServices.findByJobId(jobId);
			model.addAttribute("reviewList", reviewList);
			float rating = 0;
			for (ReviewDTO r : reviewList) {
				rating += r.getRating();
			}
			rating = rating / reviewList.size();
			model.addAttribute("rating", rating);
		} catch (Exception e) {
			e.getMessage();
		}
		ReviewDTO review = new ReviewDTO();
		model.addAttribute("review", review);
		return "job";
	}
}
