package com.fpoly.java6.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;
	
	
	@Column(name = "date")
	private Date date;
	@Column(name = "status")
	private int status;
	@Column(name = "description",columnDefinition = "nvarchar(255)")
	private String description;
	@Column(name = "total_price")
	private int total_price;
	@Column(name = "payment_method")
	private int payment_method;
	@Column(name = "fee")
	private int fee;
	@Column(name = "full_address",columnDefinition = "nvarchar(355)")
	private String full_address;
	
	@OneToMany(mappedBy = "order")
	private List<Order_Detail> order_Details = new ArrayList<Order_Detail>();
}


