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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.JohnHaney.OpenJob.LoadDictionaries;
import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.Photo;
import com.JohnHaney.OpenJob.models.User;
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

	@GetMapping("/register")
	public String adduser(Model modelUsers, Model modelContires) {
		// object of Users
		User newUser = new User();
		ArrayList<String> countries = new ArrayList<>();
		countries = LoadDictionaries.initiateDicationary();
		modelContires.addAttribute("countries", countries);

//		System.out.println("test");
//		for(String c: countries)
//			if(countries.isEmpty())
//				System.out.println("array is empty");
//			else 
//			System.out.println(c);
		// container for new user

		modelUsers.addAttribute("newUser", newUser);

		return "register";
	}

	@GetMapping("/register/edit/{id}")
	public String editProfile(Model model, @PathVariable("id") Long userId) {
		User editUser = userService.findById(userId);
		System.out.println(editUser.toString());
		ArrayList<String> countries = new ArrayList<>();
		countries = LoadDictionaries.initiateDicationary();
		model.addAttribute("countries", countries);
		model.addAttribute("newUser", editUser);
		return "editProfile";
	}

	@GetMapping("/deleteProfile/{id}")
	public String deleteProfile(Model model, @PathVariable("id") Long userId, HttpSession session) {
		System.out.println("deleting user...");
		User user = userService.findById(userId);
		
		//try to delete all photos related to User
		try {
			List<Photo> photo = photoServices.findByUser(user);
			for (Photo p : photo) {
				photoServices.deleteById(p.getPhotoId());
			}
		} catch (Exception e) {
			e.getMessage();
		}
		
		//try to delete all jobs from user
		
		
		userService.deleteById(userId);
		System.out.println("user was Deleted... ");
		session.removeAttribute("currentUser");
		return "index";
	}

	@PostMapping("/registerUser")
	public ModelAndView saveUser(@Valid @ModelAttribute("newUser") User newUser,
			@RequestParam("imageFile") MultipartFile imageFile, BindingResult bind) {
		ModelAndView modelAndView = new ModelAndView();
		// newUser.toString();
		try {
			if (!userService.existsByUsername(newUser.getUsername())) {
				userService.save(newUser);
			} else {
				// modelAndView.; TODO add error messages
				// modelAndView.setViewName("register");
				System.out.println("User already exists");
			}
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
			photo.setUser(newUser);

			modelAndView.addObject("profilePicture", photo);
			try {
				photoServices.saveImage(imageFile, photo);
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView.setViewName("error");
				return modelAndView;
			}
		}
		modelAndView.setViewName("redirect:/login");
		modelAndView.addObject("newUser", newUser);
		return modelAndView;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/profile/{userProfileID}")
	public ModelAndView findUserProfile(@PathVariable(value = "userProfileID") Long userId) {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("profile");

		User activeUser = userRepo.findById(userId).get();

		modelAndView.addObject("user", activeUser);

		ArrayList<Photo> photos = (ArrayList<Photo>) photoServices.findAll();
		for (int i = 0; i < photos.size(); i++) {
			try {
				if (photos.get(i).getUser().getUserId() == userId) {
					Photo profilePicture = photos.get(i);
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
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/editProfile")
	public ModelAndView getEditUserProfile(ModelAndView session) {
		User currentUser = userService.findByUsername(SecurityUtils.getUser());
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
