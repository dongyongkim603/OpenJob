package com.JohnHaney.OpenJob.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnHaney.OpenJob.models.Order;

public interface OrderRepoIF extends JpaRepository<Order, Integer> {

}
