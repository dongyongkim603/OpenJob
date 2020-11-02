package com.JohnHaney.OpenJob.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.JohnHaney.OpenJob.models.JobDTO;

public interface JobRepoIF extends JpaRepository<JobDTO, Long> {
	Optional<JobDTO> findByJobName(String name);
	
	@Query("SELECT j FROM JobDTO j WHERE j.jobName LIKE %?1%"
			+ "OR j.jobDescription LIKE %?1%")
	Optional<List<JobDTO>> findAll(String keyword);
}
