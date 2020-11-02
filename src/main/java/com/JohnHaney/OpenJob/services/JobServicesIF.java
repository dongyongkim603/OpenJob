package com.JohnHaney.OpenJob.services;

import org.springframework.data.domain.Page;

import com.JohnHaney.OpenJob.models.JobDTO;

public interface JobServicesIF {
	Page<JobDTO> findPaginated(int pageNumber, int pageSize);
}
