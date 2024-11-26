package com.fpoly.java6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Color;


public interface ColorJPA extends JpaRepository<Color, Integer>{

}
