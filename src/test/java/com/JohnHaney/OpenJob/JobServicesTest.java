package com.JohnHaney.OpenJob;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.services.JobServices;

@SpringBootTest
class JobServicesTest {

	@Autowired
	JobServices jobServices;
	
	@Test
	void findAllLikeTest() {
		
		String keyword = "test";
		List<JobDTO> jobList = jobServices.findAll(keyword);
		for(JobDTO j: jobList) {
			System.out.println("Job name:--------------" + j.getJobName());
		}
		
		assertNotNull(jobList);
	}
}
