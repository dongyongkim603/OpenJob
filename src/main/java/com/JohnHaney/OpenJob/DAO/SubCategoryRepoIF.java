package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.SubCategoryDTO;

public interface SubCategoryRepoIF extends JpaRepository<SubCategoryDTO, Integer>{

}
