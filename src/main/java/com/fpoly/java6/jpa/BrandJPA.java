package com.fpoly.java6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Brand;

public interface BrandJPA extends JpaRepository<Brand, Integer>{
	Brand findByName(String name); 
}
