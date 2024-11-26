package com.fpoly.java6.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandWithProductsDto {
	private String brandName;
	private List<ProductDto> productDtos;
}
