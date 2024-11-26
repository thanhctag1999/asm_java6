package com.fpoly.java6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Discount;


public interface DiscountJPA extends JpaRepository<Discount, Integer>{

}
