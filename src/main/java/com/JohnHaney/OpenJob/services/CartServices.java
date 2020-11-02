package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.CartRepoIF;
import com.JohnHaney.OpenJob.models.CartDTO;
import com.JohnHaney.OpenJob.models.JobDTO;

@Service
public class CartServices {

	@Autowired
	CartRepoIF cartRepo;
	
	public void deleteById(Integer id) {
		if (existsById(id))
			cartRepo.deleteById(id);
	}
	
	public void save(CartDTO cart) {		
		cartRepo.save(cart);
	}
	
	public CartDTO findById(Integer id) {
		return cartRepo.findById(id).get();
	}
	
	public boolean existsById(Integer id) {
		return cartRepo.existsById(id);
	}
	
	/**
	 * Will make a query to find a list of all CartDTO's that match the jobId parameter
	 * @param job the jobId to be searched for
	 * @return A list of CartDTO's
	 */
	public List<CartDTO> findByJob(Long job){
		return cartRepo.findByJobsJobId(job).get();
	}
}
