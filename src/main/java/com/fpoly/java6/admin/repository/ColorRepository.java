package com.fpoly.java6.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.java6.entities.Color;


public interface ColorRepository extends JpaRepository<Color, Integer> {
	
}
