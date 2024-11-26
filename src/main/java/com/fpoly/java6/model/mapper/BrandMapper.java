package com.fpoly.java6.model.mapper;

import com.fpoly.java6.entities.Brand;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.model.dto.BrandDto;
import com.fpoly.java6.model.dto.VariantDto;

public class BrandMapper {
	public static BrandDto tBrandDto(Brand brand) {
        BrandDto brandDto = new BrandDto();
        brandDto.setId(brand.getId());
        brandDto.setName(brand.getName());
        brandDto.setImage(brand.getImage());
        brandDto.setProducts(brand.getProducts());

        return brandDto;
    }
}
