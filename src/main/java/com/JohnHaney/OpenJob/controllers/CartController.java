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
		System.out.println("retrieved user from session****************************************");
		CartDTO cart = (CartDTO) session.getAttribute("cart");
		if(cart == null) {
			System.out.println("cart is null*****************************");
			cart = new CartDTO();
		}
		System.out.println("retrieved cart from session****************************************");
		
		//try to find job in DB
		try {
			job = jobServices.findById(jobId);
			System.out.println("job was found****************************************");
		} catch (Exception e) {
			e.getLocalizedMessage();
			System.out.println("job not was found****************************************");
		}
		
		//try to find user in DB
		try {
			user = userServices.findByUsername(user.getUsername());
			System.out.println("user was found****************************************");
		} catch (Exception e) {
			e.getLocalizedMessage();
			System.out.println("user was not found****************************************");
		}
		
		cart.addJobToCart(job);
		System.out.println("job was added to cart properly****************************************");
		cart.setShopper(user);
		System.out.println("cart was assigned a user properly****************************************");
		cart.updateTotal();
		System.out.println("cart total was updated****************************************");
		user.setCart(cart);
		System.out.println("cart was properly assigned to user****************************************");
		//try to persist user with updated cart and cart to DB
		try {
			userServices.save(user);
			System.out.println("user was saved properly****************************************");
		}catch(Exception e) {
			e.getLocalizedMessage();
			System.out.println("unable to save user****************************************");
		}
		
		try {
			cartServices.save(cart);
			System.out.println("cart was persisted properly****************************************");
			
		} catch(Exception e){
			e.getLocalizedMessage();
			System.out.println("unable to persist cart****************************************");
		}
		session.removeAttribute("cart");
		session.setAttribute("cart", cart);
		return "redirect:/" + returnPage;
	}
}
