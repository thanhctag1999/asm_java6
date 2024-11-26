package com.fpoly.java6.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

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
   // @JsonManagedReference
    private List<VariantDto> variants;  // Danh sách các variant của sản phẩm

    @JsonProperty("brand")
    private String brandName;  // Tên brand, thay vì đối tượng Brand

    @JsonProperty("type")
    private String typeName;  // Tên type, thay vì đối tượng Type
}
