package com.fpoly.java6.entities;

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
@Table(name = "addresses")
public class Address {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "account_id",columnDefinition = "nvarchar(255)")
	private Account account;
	
	
	@Column(name = "provinceid",columnDefinition = "nvarchar(255)")
	private String provinceId;
	
	@Column(name = "districtid",columnDefinition = "nvarchar(255)")
	private String districtId;
	
	@Column(name = "wardcode",columnDefinition = "nvarchar(255)")
	private String wardCode;
	
	@Column(name = "address",columnDefinition = "nvarchar(255)")
	private String address;
	
	@Column(name = "full_address",columnDefinition = "nvarchar(255)")
	private String full_address;
	
	@Column(name = "isactived")
	private boolean isActived;
}
