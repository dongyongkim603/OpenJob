package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.MessageDTO;

public interface MessageRepoIF extends JpaRepository<MessageDTO, Integer> {

}
