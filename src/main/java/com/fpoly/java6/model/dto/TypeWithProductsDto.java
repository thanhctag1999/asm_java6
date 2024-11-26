package com.fpoly.java6.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeWithProductsDto {
	private String typeName;
	private List<ProductDto> productDtos;
}
