package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Review;

public interface ReviewRepoIF extends JpaRepository<Review, Integer>{

}
