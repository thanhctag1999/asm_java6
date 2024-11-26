package com.fpoly.java6.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpoly.java6.entities.Variant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {
	@JsonProperty("id")
	private int id;
	@JsonProperty("size")
	private int size;
//	 @JsonProperty("variants")
//	   private List<VariantDto> variants = new ArrayList<>();
}