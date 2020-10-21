package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.DAO.IPhotoDAO;
import com.JohnHaney.OpenJob.DAO.PhotoRepoIF;
import com.JohnHaney.OpenJob.models.Job;
import com.JohnHaney.OpenJob.models.Photo;

@Service
public class PhotoServices {

	@Autowired
	PhotoRepoIF photoRepo;
	
	@Autowired
	IPhotoDAO photoDAO;
	
	public void saveImage(MultipartFile imageFile, Photo photo) throws Exception{
		photoDAO.save(photo);
		photoDAO.savePhotoImage(photo, imageFile);
	}
	
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
	
	public void save(Photo photo) {		
		photoRepo.save(photo);
	}
	
//	public List<Photo> findAll() {
//		return photoRepo.findAll();
//	}
	
	public Photo findById(Long id) {
		return photoRepo.findById(id).get();
	}
	
	public boolean existsById(Long id) {
		return photoRepo.existsById(id);
	}
}
