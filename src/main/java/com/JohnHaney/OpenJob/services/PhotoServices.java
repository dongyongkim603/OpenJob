package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.DAO.IPhotoDAO;
import com.JohnHaney.OpenJob.DAO.PhotoRepoIF;
import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.models.PhotoDTO;
import com.JohnHaney.OpenJob.models.UserDTO;

@Service
public class PhotoServices {

	@Autowired
	PhotoRepoIF photoRepo;
	
	@Autowired
	IPhotoDAO photoDAO;
	
	/**
	 * Save the MultipartFile data to a specified directory and save the other photo details to the database
	 * @param imageFile contains the Byte date of the photo file
	 * @param photo the PhotoDTO which contains path details and forign key constraints
	 * @throws Exception 
	 */
	public void saveImage(MultipartFile imageFile, PhotoDTO photo) throws Exception{
		photoDAO.save(photo);
		photoDAO.savePhotoImage(photo, imageFile);
	}
	
	/**
	 * will check to see if a photo 
	 * @param name
	 * @return
	 */
	public boolean existsByName(String name) {
			if(null == photoRepo.findByFileName(name))
				return true;
			else
				return false;
	}
	
	public void deleteById(Long id) {
		if (existsById(id))
			photoRepo.deleteById(id);
	}
	
	public void save(PhotoDTO photo) {		
		photoRepo.save(photo);
	}
	
	public List<PhotoDTO> findAll() {
		return photoRepo.findAll();
	}
	
	public PhotoDTO findById(Long id) {
		return photoRepo.findById(id).get();
	}
	
	public List<PhotoDTO> findByJob(JobDTO job) {
		return photoRepo.findAllByJob(job).get();
	}
	
	public boolean existsById(Long id) {
		return photoRepo.existsById(id);
	}
	
	public List<PhotoDTO> findByUser(UserDTO user) {
		return photoRepo.findAllByUser(user).get();
	}
}
