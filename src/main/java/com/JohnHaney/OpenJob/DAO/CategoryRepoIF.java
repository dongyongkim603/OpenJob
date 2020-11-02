package com.JohnHaney.OpenJob.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.CategoryDTO;

public interface CategoryRepoIF extends JpaRepository<CategoryDTO, Integer> {

}
