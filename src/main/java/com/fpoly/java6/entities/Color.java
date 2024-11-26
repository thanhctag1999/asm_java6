package com.fpoly.java6.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "colors")
public class Color {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "color",columnDefinition = "nvarchar(255)")
	private String color;
	
	@OneToMany(mappedBy = "color")
	@JsonManagedReference
	private List<Variant> variants = new ArrayList<>();
	
	   @Override
	    public String toString() {
	        return color;  // Trả về tên màu
	    }
}
