package com.JohnHaney.OpenJob.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.models.ReviewDTO;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.ReviewServices;

@Controller
public class ReviewController {

	@Autowired
	JobServices jobServices;

	@Autowired
	ReviewServices reviewServices;

	@PostMapping("/{jobId}/jobReview")
	public String postJobReview(Model model, @PathVariable("jobId") Long jobId,
			@Valid @ModelAttribute("review") ReviewDTO review, HttpSession session) {
		JobDTO job = jobServices.findById(jobId);
		String returnPage = "";
		try {
			review.setCreationDate(LocalDate.now());
			review.setSender((UserDTO) session.getAttribute("currentUser"));
			review.setJob(job);
			reviewServices.save(review);
		} catch (Exception e) {
			System.out.println("review failed to save");
			returnPage = "error";
		}
		try {
			List<ReviewDTO> reviews = job.getJobReview();
			reviews.add(review);
			job.setJobReview(reviews);
			jobServices.save(job);
			returnPage = "job/" + jobId.toString();
			System.out.println(returnPage);
		} catch (Exception e) {
			System.out.println("job failed to save");
			returnPage = "error";
		}

		return "redirect:/"+returnPage;
	}

	@GetMapping("/deleteReview/{reviewId}/{jobId}")
	public String deleteReview(@PathVariable("reviewId") Long reviewId, @PathVariable("jobId") Long jobId) {
		String returnPage = "redirect:/job/" + jobId.toString();
		try {
		reviewServices.deleteById(reviewId);
		}catch(Exception e) {
			e.getLocalizedMessage();
		}
		return returnPage;
	}
	
	
}
