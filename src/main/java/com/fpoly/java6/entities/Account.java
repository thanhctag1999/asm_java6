package com.fpoly.java6.entities;

import java.util.ArrayList;
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
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name",columnDefinition = "nvarchar(255)")
    private String name;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "gender")
    private boolean gender;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "image")
    private String image;
    
    @Column(name = "role")
    private int role;
    
    @Column(name = "isactived")
    private boolean isActived;
    
    @OneToMany(mappedBy = "account")
	private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "account")
	private List<Order> orders = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "account")
	private List<Favorite> favorites = new ArrayList<>();
    
    @OneToMany(mappedBy = "account")
	private List<Cart_Detail> cart_Details = new ArrayList<>();

	public Account orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
