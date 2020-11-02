package com.JohnHaney.OpenJob.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.JohnHaney.OpenJob.LoadDictionaries;
import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.PhotoDTO;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.security.SecurityUtils;
import com.JohnHaney.OpenJob.services.PhotoServices;
import com.JohnHaney.OpenJob.services.UserServices;

@Controller
public class UserController {

	@Autowired
	UserServices userService;

	@Autowired
	PhotoServices photoServices;

	@Autowired
	UserRepoIF userRepo;

	/**
	 * forwards the user to the registration page and generates new UserDTO to be
	 * added to the model.
	 * 
	 * @param modelUsers    stores the new UserDTO
	 * @param modelContires stores the list of countries
	 * @return sends user to the registration page
	 */
	@GetMapping("/register")
	public String addUser(Model modelUsers, Model modelContires) {
		// object of Users
		UserDTO newUser = new UserDTO();

		// calls static method that reads list of countries to be added to view
		ArrayList<String> countries = new ArrayList<>();
		countries = LoadDictionaries.initiateDicationary();
		modelContires.addAttribute("countries", countries);
		modelUsers.addAttribute("newUser", newUser);

		return "register";
	}

	/**
	 * Allows the user to modify the data that is related to their UserDTO.
	 * Retrieves the user by searching the database based on the PathVarible of the
	 * target user and passes the DTO to the model
	 * 
	 * @param model  stores the UserDTO data retrieved from the database
	 * @param userId the userId of the target user
	 * @return will send users to the editProfile view page
	 */
	@GetMapping("/register/edit/{id}")
	public String editProfile(Model model, @PathVariable("id") Long userId) {
		UserDTO editUser = userService.findById(userId);
		System.out.println(editUser.toString());
		ArrayList<String> countries = new ArrayList<>();
		countries = LoadDictionaries.initiateDicationary();
		model.addAttribute("countries", countries);
		model.addAttribute("newUser", editUser);
		return "editProfile";
	}

	/**
	 * TODO: finish delete method once all related DTO controllers have been
	 * implemented Allows user to remove their data from the system.
	 * 
	 * @param model
	 * @param userId
	 * @param session
	 * @return
	 */
	@GetMapping("/deleteProfile/{id}")
	public String deleteProfile(Model model, @PathVariable("id") Long userId, HttpSession session) {
		System.out.println("deleting user...");
		UserDTO user = userService.findById(userId);

		// try to delete all photos related to User
		try {
			List<PhotoDTO> photo = photoServices.findByUser(user);
			for (PhotoDTO p : photo) {
				photoServices.deleteById(p.getPhotoId());
			}
		} catch (Exception e) {
			e.getMessage();
		}

		// try to delete all jobs from user

		userService.deleteById(userId);
		System.out.println("user was Deleted... ");
		session.removeAttribute("currentUser");
		return "index";
	}

	/**
	 * Allows the user to persist the data that was taken from the registration page
	 * as a new UserDTO.
	 * 
	 * @param newUser   the data taken from the model that was entered from the view
	 *                  by the user
	 * @param imageFile the multipartFile data taken from the page. Contains the
	 *                  Byte data of the uploaded photo
	 * @param bind
	 * @return will redirect the user to the login view page
	 */
	@PostMapping("/registerUser")
	public ModelAndView saveUser(@Valid @ModelAttribute("newUser") UserDTO newUser,
			@RequestParam("imageFile") MultipartFile imageFile, BindingResult bind) {
		ModelAndView modelAndView = new ModelAndView();
		// newUser.toString();
		try {
			userService.save(newUser);
			modelAndView.setViewName("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("register");
			String errorMessage = "The email or username is already in use";
			modelAndView.addObject("errorMessage", errorMessage);
			ArrayList<String> countries = new ArrayList<>();
			countries = LoadDictionaries.initiateDicationary();
			modelAndView.addObject("countries", countries);
		}
		if (!imageFile.isEmpty()) {
			// create new photo from uploaded MultipartFile
			PhotoDTO photo = new PhotoDTO();
			String fileName = imageFile.getOriginalFilename();
			photo.setFileName(fileName);
			photo.setPath("/photos/");// this line will override photos with same name change this hard coded line to
										// be dynamic
			photo.setUser(newUser);
			modelAndView.setViewName("redirect:/login");
			modelAndView.addObject("profilePicture", photo);
			try {
				photoServices.saveImage(imageFile, photo);
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView.setViewName("register");
				return modelAndView;
			}
		}

		modelAndView.addObject("newUser", newUser);
		return modelAndView;
	}

	/**
	 * Finds the target user and retrieves the data from the database. The found
	 * UserDTO is passed to the ModelAndView to dynamically populate the user
	 * profile with the user details
	 * 
	 * @param userId the target UserDTO's userId
	 * @return will send the users to the target userProfile view page
	 */
	@GetMapping("/profile/{userProfileID}")
	public ModelAndView findUserProfile(@PathVariable(value = "userProfileID") Long userId) {
		ModelAndView modelAndView = new ModelAndView();
		UserDTO activeUser = new UserDTO();
		modelAndView.setViewName("profile");

		try {
			activeUser = userRepo.findById(userId).get();
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		modelAndView.addObject("user", activeUser);

		ArrayList<PhotoDTO> photos = (ArrayList<PhotoDTO>) photoServices.findAll();
		for (int i = 0; i < photos.size(); i++) {
			try {
				if (photos.get(i).getUser().getUserId() == userId) {
					PhotoDTO profilePicture = photos.get(i);
					modelAndView.addObject("profilePicture", profilePicture);
					return modelAndView;
				}
			} catch (Exception e) {
				e.getLocalizedMessage();
				System.out.println("not a user picture");
			}
		}
		return modelAndView;
	}

	/**
	 * Finds the current user and creates a UserDTO to be passed to the session. This data will be used by the update handler
	 * method
	 * @param session stores the current users details
	 * @return sends the user to the editProfile view page
	 */
	@GetMapping("/editProfile")
	public ModelAndView getEditUserProfile(ModelAndView session) {
		UserDTO currentUser = new UserDTO();
		try {
			currentUser = userService.findByUsername(SecurityUtils.getUser());
		}catch(Exception e) {
			e.getLocalizedMessage();
		}
		
		session.addObject("currentUser", currentUser);
		session.setViewName("user/editProfile");
		return session;
	}

	/**
	 * sends the user to the login page and also creates an in memory admin id and
	 * password
	 * 
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
	 * 
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
}
