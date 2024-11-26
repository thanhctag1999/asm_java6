package com.fpoly.java6.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto {
	@JsonProperty("id")
	private int id;
	@JsonProperty("color")
	private String color;
}
