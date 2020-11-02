package com.JohnHaney.OpenJob.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.JohnHaney.OpenJob.models.CartDTO;
import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.models.ReviewDTO;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.services.CartServices;
import com.JohnHaney.OpenJob.services.JobServices;
import com.JohnHaney.OpenJob.services.UserServices;

@Controller
public class CartController {

	@Autowired
	JobServices jobServices;

	@Autowired
	UserServices userServices;
	
	@Autowired
	CartServices cartServices;

	/**
	 * Allows the user to add a job to their cart. Will capture the data from the session cart search the database for the users cart and added the selected job.
	 * @param model the model used to added the objects to 
	 * @param jobId the jobId captured from the PathVariable and retrieved from the database
	 * @param userCart the cart captured from the users session
	 * @param session the HttpSession that contains all the data needed for the user durring thier session
	 * @return
	 */
	@PostMapping("/{jobId}/addToCart")
	public String addJobToOrder(Model model, @PathVariable("jobId") Long jobId,
			@Valid @ModelAttribute("userCart") CartDTO userCart, HttpSession session) {
		String returnPage = "job/" + jobId.toString();
		JobDTO job = new JobDTO();
		UserDTO user = (UserDTO) session.getAttribute("currentUser");
		CartDTO cart = (CartDTO) session.getAttribute("cart");
		
		//try to find job in DB
		try {
			job = jobServices.findById(jobId);

		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		
		//try to find user in DB
		try {
			user = userServices.findByUsername(user.getUsername());
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		
		cart.addJobToCart(job);
		cart.setShopper(user);
		cart.updateTotal();
		user.setCart(cart);
		
		//try to persist user with updated cart and cart to DB
//		try {
//			userServices.save(user);
//		}catch(Exception e) {
//			e.getLocalizedMessage();
//		}
		
		try {
			cartServices.save(cart);
		} catch(Exception e){
			e.getLocalizedMessage();
		}
		
		return "redirect:/" + returnPage;
	}
}
