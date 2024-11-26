package com.fpoly.java6.entities;

import java.util.ArrayList;
import java.util.List;

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

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "variants")
public class Variant {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "size_id")
	
	private Size size;
	
	@ManyToOne
	@JoinColumn(name = "color_id")
	
	private Color color;
	
	@ManyToOne
	@JoinColumn(name = "product_id")

	private Product product;
	
	@Column(name = "price")
	private int price;
	@Column(name = "quantity")
	private int quantity;
	
	@OneToMany(mappedBy = "variant")

	private List<Order_Detail> order_Details = new ArrayList<>();
}
