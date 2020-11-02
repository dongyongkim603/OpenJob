package com.JohnHaney.OpenJob.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.ReviewDTO;

public interface ReviewRepoIF extends JpaRepository<ReviewDTO, Long>{
	Optional<List<ReviewDTO>> findByJobJobId(Long jobId);
}
