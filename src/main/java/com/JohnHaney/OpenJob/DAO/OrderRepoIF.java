package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.OrderDTO;

public interface OrderRepoIF extends JpaRepository<OrderDTO, Integer> {

}
