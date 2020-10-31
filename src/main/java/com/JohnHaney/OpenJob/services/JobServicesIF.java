package com.JohnHaney.OpenJob.services;

import org.springframework.data.domain.Page;

import com.JohnHaney.OpenJob.models.Job;

public interface JobServicesIF {
	Page<Job> findPaginated(int pageNumber, int pageSize);
}
