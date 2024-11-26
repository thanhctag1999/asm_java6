package com.fpoly.java6.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Size;



public interface SizeJPA extends JpaRepository<Size, Integer>{
	
}
