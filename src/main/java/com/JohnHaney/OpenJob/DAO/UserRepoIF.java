package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.User;

public interface UserRepoIF extends JpaRepository<User, Integer>{

}
