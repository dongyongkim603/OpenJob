package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Job;

public interface JobRepoIF extends JpaRepository<Job, Integer> {

}
