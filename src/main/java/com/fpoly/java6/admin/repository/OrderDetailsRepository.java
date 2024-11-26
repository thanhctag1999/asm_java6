package com.fpoly.java6.admin.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpoly.java6.entities.Order_Detail;


@Repository
public interface OrderDetailsRepository extends JpaRepository<Order_Detail, Integer> {
	public List<Order_Detail> findByOrder_Id(int id);



}