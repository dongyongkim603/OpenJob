package com.JohnHaney.OpenJob.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.CartDTO;
import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.models.PhotoDTO;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.security.SecurityUtils;
import com.JohnHaney.OpenJob.services.CartServices;
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

	@Autowired
	UserServices userServices;

	@Autowired
	CartServices cartServices;

	/**
	 * The initial handler method. Will redirect users to index page and generate
	 * dynamic session data for user cart and session
	 * 
	 * @param session contains the dynamic objects that specific to the users
	 *                session
	 * @return the index view
	 */
	@RequestMapping("/")
	public String showHomePage(HttpSession session, Model jobPostModel) {
		UserDTO user = new UserDTO();
		CartDTO cart = new CartDTO();
		boolean signedIn = false;
		try {
			user = userRepo.findByUsername(SecurityUtils.getUser()).get();
			if (user.getEmail() != null) {
				session.setAttribute("currentUser", user);
				signedIn = true;
			}
		} catch (NoSuchElementException e) {
			e.getMessage();
		}

		if (signedIn) {
			try {
				cart = cartServices.findById(user.getCart().getCartId());
				session.setAttribute("cart", cart);
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
		}

		List<JobDTO> jobList = jobServices.findAll();
//		List<Photo> photos = photoServices.findByJob(jobList.get(0));
		List<PhotoDTO> photos = photoServices.findAll();
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

		PhotoDTO photo = new PhotoDTO();
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

	/**
	 * Sends the user to the locations view page
	 * 
	 * @return the location view page
	 */
	@RequestMapping("/location")
	public String getLocationPage() {
		return "location";
	}

	/**
	 * Sends the user to the about view page
	 * 
	 * @return the about view
	 */
	@RequestMapping("/about")
	public String getAboutPage() {
		return "about";
	}

	/**
	 * TODO finish pagination
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@GetMapping("/page/{pageNumber}/{pageSize}")
	public String findPagination(@PathVariable(value = "pageNumber") int pageNumber,
			@PathVariable(value = "pageSize") int pageSize, Model model) {
		Page<JobDTO> page = jobServices.findPaginated(pageNumber, pageSize);
		List<JobDTO> jobList = page.getContent();

		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalJobs", page.getNumberOfElements());
		model.addAttribute("jobList", jobList);
		return "index";
	}

	/**
	 * Utilizes custom queries made at the repository layer to search through
	 * database for keywords that appear in the JobDTO and UserDTO tables.
	 * 
	 * @param keyword the search term entered by the user to look for either jobs or
	 *                users
	 * @param model   used to store the lists of found DTO's
	 * @return sends the user to the search results page
	 */
	@GetMapping("/search")
	public String searchResults(@Valid @ModelAttribute("keyword") String keyword, Model model) {
		String returnPage = "";
		System.out.println("the keyword is: " + keyword + "*****************************");

		// try to search database job entries that match keyword
		try {
			List<JobDTO> jobList = jobServices.findAll(keyword);
			System.out.println("search for jobs in database was successfull *********************************");
			model.addAttribute("jobList", jobList);
		} catch (Exception e) {
			e.getLocalizedMessage();
			System.out.println("search for jobs in database was unsuccessfull *********************************");
		}

		// try to search database job entries that match keyword
		try {
			List<UserDTO> userList = userServices.findAll(keyword);
			System.out.println("search for jobs in database was successfull *********************************");
			model.addAttribute("userList", userList);

		} catch (Exception e) {
			e.getLocalizedMessage();
			System.out.println("search for jobs in database was unsuccessfull *********************************");
		}
		returnPage += "searchResults";
		model.addAttribute("keyword", keyword);
		return returnPage;
	}
}
