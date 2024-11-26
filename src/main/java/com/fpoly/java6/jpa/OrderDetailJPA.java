package com.fpoly.java6.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Order_Detail;

public interface OrderDetailJPA  extends JpaRepository<Order_Detail, Integer>{
	
}
