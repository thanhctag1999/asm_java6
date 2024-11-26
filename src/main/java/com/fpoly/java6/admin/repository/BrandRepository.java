package com.fpoly.java6.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Brand;


public interface BrandRepository extends JpaRepository<Brand, Integer> {
	void deleteById(int id);


}
