package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.DAO.IPhotoDAO;
import com.JohnHaney.OpenJob.DAO.JobRepoIF;
import com.JohnHaney.OpenJob.models.Job;
import com.JohnHaney.OpenJob.models.Photo;

@Service
public class JobServices {
	
	@Autowired
	JobRepoIF jobRepo;
	
	public boolean existsByName(String name) {
			if(null == jobRepo.findByJobName(name))
				return true;
			else
				return false;
	}
	
	public void deleteById(Long id) {
		if (existsById(id))
			jobRepo.deleteById(id);
	}
	
	public void save(Job user) {		
		jobRepo.save(user);
	}
	
	public List<Job> findAll() {
		return jobRepo.findAll();
	}
	
	public Job findById(Long id) {
		return jobRepo.findById(id).get();
	}
	
	public boolean existsById(Long id) {
		return jobRepo.existsById(id);
	}
}
