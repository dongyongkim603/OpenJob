package com.JohnHaney.OpenJob.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Job;

public interface JobRepoIF extends JpaRepository<Job, Long> {
	Optional<Job> findByJobName(String name);
}
