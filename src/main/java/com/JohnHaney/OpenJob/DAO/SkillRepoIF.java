package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Skill;

public interface SkillRepoIF extends JpaRepository<Skill, Integer> {

}
