package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.SkillDTO;

public interface SkillRepoIF extends JpaRepository<SkillDTO, Integer> {

}
