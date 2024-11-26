package com.fpoly.java6.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	
	@ManyToOne
	@JoinColumn(name = "order_detail_id")
	private Order_Detail order_detail;
	
	
	@Column(name = "rating")
	private int rating;
	@Column(name = "description",columnDefinition = "nvarchar(255)")
	private String description;
	@Column(name = "status")
	private int status;
	@Column(name = "date")
	private Date date;
}
