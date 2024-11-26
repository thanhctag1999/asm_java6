package com.fpoly.java6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
	private int id;
	private int accountId;
	private ProductDto product;
	private int price;
	private int quantity;
	private int variantId;
	private VariantDto variant;
	
	public int getTotalPrice() {
	        return price * quantity;
	}
}
