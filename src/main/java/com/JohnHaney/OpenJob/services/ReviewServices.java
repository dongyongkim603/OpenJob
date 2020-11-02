package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.ReviewRepoIF;
import com.JohnHaney.OpenJob.models.ReviewDTO;

@Service
public class ReviewServices {

	@Autowired
	ReviewRepoIF reviewRepo;
	
	public void deleteById(Long id) {
		if (existsById(id))
			reviewRepo.deleteById(id);
	}
	
	public void save(ReviewDTO user) {		
		reviewRepo.save(user);
	}
	
	public List<ReviewDTO> findAll() {
		return reviewRepo.findAll();
	}
	
	public ReviewDTO findById(Long id) {
		return reviewRepo.findById(id).get();
	}
	
	public boolean existsById(Long id) {
		return reviewRepo.existsById(id);
	}
	
	public List<ReviewDTO> findByJobId(Long jobId){
		return reviewRepo.findByJobJobId(jobId).get();
	}
}
