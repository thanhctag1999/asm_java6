package com.fpoly.java6.admin.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpoly.java6.entities.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}