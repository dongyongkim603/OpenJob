package com.JohnHaney.OpenJob.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.JobDTO;
import com.JohnHaney.OpenJob.models.PhotoDTO;
import com.JohnHaney.OpenJob.models.UserDTO;

public interface PhotoRepoIF extends JpaRepository<PhotoDTO, Long> {
	Optional<PhotoDTO> findByFileName(String fileName);
	Optional<List<PhotoDTO>> findAllByJob(JobDTO jobId);
	Optional<List<PhotoDTO>> findAllByUser(UserDTO userId);
}
