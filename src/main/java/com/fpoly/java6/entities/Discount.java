package com.fpoly.java6.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Table(name = "discounts")
public class Discount {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "code")
	private String code;
	@Column(name = "discount_value")
	private int discount_value;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "description",columnDefinition = "nvarchar(255)")
	private String description;
	@Column(name = "start_date")
	private Date start_date;
	@Column(name = "end_date")
	private Date end_date;
	
	
//	private List<Order> orders = new ArrayList<Order>();
}
