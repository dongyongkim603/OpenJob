package com.JohnHaney.OpenJob.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.JobDTO;

public interface JobRepoIF extends JpaRepository<JobDTO, Long> {
	Optional<JobDTO> findByJobName(String name);
}
