package com.fpoly.java6.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpoly.java6.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String name;

	@JsonProperty("product")
	private List<Product> product;
}
