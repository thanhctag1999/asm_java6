package com.fpoly.java6.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	@JsonBackReference
	private Type type;	
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	@JsonBackReference
	private Brand brand;
	
	
	@Column(name = "name",columnDefinition = "nvarchar(255)")
	private String name;
	@Column(name = "description", length = 1000,columnDefinition = "nvarchar(max)")
	private String description;
	@Column(name = "date")
	private Date date;
	@Column(name = "image")
	private String image;
	@Column(name = "status")
	private int status;
	
	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<Variant> variants = new ArrayList<>();;
	
	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<Favorite> favorites = new ArrayList<>();
}
