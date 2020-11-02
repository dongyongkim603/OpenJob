package com.JohnHaney.OpenJob.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.JohnHaney.OpenJob.models.JobDTO;

@Component
public class JobDAO {

	@Autowired
	JobRepoIF jobRepo;
	
	public boolean save(JobDTO job) throws Exception{
		JobDTO savedJob = jobRepo.save(job);
		job = savedJob;
		return false;
	}
}
