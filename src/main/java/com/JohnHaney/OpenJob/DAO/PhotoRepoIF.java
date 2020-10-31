package com.JohnHaney.OpenJob.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Job;
import com.JohnHaney.OpenJob.models.Photo;
import com.JohnHaney.OpenJob.models.User;

public interface PhotoRepoIF extends JpaRepository<Photo, Long> {
	Optional<Photo> findByFileName(String fileName);
	Optional<List<Photo>> findAllByJob(Job jobId);
	Optional<List<Photo>> findAllByUser(User userId);
}
