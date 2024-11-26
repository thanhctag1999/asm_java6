package com.fpoly.java6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Order;


public interface OrderJPA extends JpaRepository<Order, Integer>{

}
