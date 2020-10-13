package com.JohnHaney.OpenJob.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Category;

public interface CategoryRepoIF extends JpaRepository<Category, Integer> {

}
