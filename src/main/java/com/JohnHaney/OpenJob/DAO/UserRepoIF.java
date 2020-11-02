package com.JohnHaney.OpenJob.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.JohnHaney.OpenJob.models.UserDTO;


public interface UserRepoIF extends JpaRepository<UserDTO, Long>{
	Optional<UserDTO> findByUsername(String username);
	
	@Query("SELECT u FROM UserDTO u WHERE u.firstName LIKE %?1%"
			+ "OR u.lastName LIKE %?1%"
			+ "OR u.username LIKE %?1%"
			+ "OR u.city LIKE %?1%"
			+ "OR u.country LIKE %?1%")
	Optional<List<UserDTO>> findAll(String keyword);
	//findByUsername(@Param("username") String username);
	//public UserDTO findByUsername(String username);
}
