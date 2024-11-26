package com.fpoly.java6.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("size")
    private int sizeName;  

    @JsonProperty("color")
    private String colorName; 

    @JsonProperty("price")
    private int price;

    @JsonProperty("quantity")
    private int quantity;
}
