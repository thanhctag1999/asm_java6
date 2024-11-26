package com.fpoly.java6.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Discount;


public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
