package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.JobRepoIF;
import com.JohnHaney.OpenJob.models.Job;

@Service
public class JobServices implements JobServicesIF {
	
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
	
	@Override
	public Page<Job> findPaginated(int pageNumber, int pageSize){
		Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
		return jobRepo.findAll(pageable);
	}
}
