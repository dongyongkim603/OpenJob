package com.JohnHaney.OpenJob.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.JohnHaney.OpenJob.models.Job;

@Component
public class JobDAO {

	@Autowired
	JobRepoIF jobRepo;
	
	public boolean save(Job job) throws Exception{
		Job savedJob = jobRepo.save(job);
		job = savedJob;
		return false;
	}
}
