package com.JohnHaney.OpenJob.DAO;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.JohnHaney.OpenJob.models.UserDTO;


public interface UserRepoIF extends JpaRepository<UserDTO, Long>{
	Optional<UserDTO> findByUsername(String username);
	//findByUsername(@Param("username") String username);
	//public UserDTO findByUsername(String username);
}
