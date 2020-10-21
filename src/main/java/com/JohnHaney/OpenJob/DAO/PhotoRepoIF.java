package com.JohnHaney.OpenJob.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.JohnHaney.OpenJob.models.Photo;
import com.JohnHaney.OpenJob.models.User;

public interface PhotoRepoIF extends CrudRepository<Photo, Long> {
	Optional<Photo> findByFileName(String fileName);
}
