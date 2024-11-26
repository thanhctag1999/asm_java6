package com.fpoly.java6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Type;



public interface TypeJPA extends JpaRepository<Type, Integer>{
	Type findByName(String name); 
}
