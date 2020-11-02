package com.JohnHaney.OpenJob.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.CartDTO;

public interface CartRepoIF extends JpaRepository <CartDTO, Integer> {
	Optional<List<CartDTO>> findByJobsJobId(Long jobId);
}
