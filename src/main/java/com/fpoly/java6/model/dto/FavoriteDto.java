package com.fpoly.java6.model.dto;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Product;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
	private int id;
	private Account account;
	private Product product;
}
