package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.JobRepoIF;
import com.JohnHaney.OpenJob.models.JobDTO;

@Service
public class JobServices implements JobServicesIF {

	@Autowired
	JobRepoIF jobRepo;

	/**
	 * Will check to see if a JobDTO of the target name exists in database
	 * 
	 * @param name the name of the job to be searched for
	 * @return true if the job exists
	 */
	public boolean existsByName(String name) {
		if (null == jobRepo.findByJobName(name))
			return true;
		else
			return false;
	}

	/**
	 * uses custom query to find all the jobs that match the userId
	 * 
	 * @param userId the target userId
	 * @return the list of jobs
	 */
	public List<JobDTO> findByUser(Long userId) {
		return jobRepo.findAll(userId).get();
	}

	/**
	 * utilizes a custom query to find a list of all the JobDTO's that match the
	 * target attributes
	 * 
	 * @param keyword the keyword phrase that the user enters to be used as a search
	 *                parameter
	 * @return will return the list of matching JobDTO
	 */
	public List<JobDTO> findAll(String keyword) {
		return jobRepo.findAll(keyword).get();
	}

	public void deleteById(Long id) {
		if (existsById(id))
			jobRepo.deleteById(id);
	}

	public void save(JobDTO user) {
		jobRepo.save(user);
	}

	public List<JobDTO> findAll() {
		return jobRepo.findAll();
	}

	public JobDTO findById(Long id) {
		return jobRepo.findById(id).get();
	}

	public boolean existsById(Long id) {
		return jobRepo.existsById(id);
	}

	@Override
	public Page<JobDTO> findPaginated(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return jobRepo.findAll(pageable);
	}
}
