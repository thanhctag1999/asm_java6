package com.fpoly.java6.model.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("image")
    private String image;

    @JsonProperty("status")
    private int status;

    @JsonProperty("variants")
//    @JsonManagedReference
    private List<VariantDto> variants;  // Danh sách các variant của sản phẩm

    @JsonProperty("brand")
    private String brandName;  // Tên brand, thay vì đối tượng Brand

    @JsonProperty("type")
    private String typeName;
}
