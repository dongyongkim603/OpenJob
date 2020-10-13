package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Message;

public interface MessageRepoIF extends JpaRepository<Message, Integer> {

}
