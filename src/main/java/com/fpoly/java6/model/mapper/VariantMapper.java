package com.fpoly.java6.model.mapper;

import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.model.dto.SizeDto;
import com.fpoly.java6.model.dto.VariantDto;
public class VariantMapper {
    public static VariantDto tVariantDto(Variant variant) {
        VariantDto variantDTO = new VariantDto();
        variantDTO.setId(variant.getId());
        variantDTO.setSizeName(variant.getSize().getSize());
        String colorHex = variant.getColor() != null ? variant.getColor().getColor() : null;
        variantDTO.setColorName(colorHex);

        variantDTO.setPrice(variant.getPrice());
        variantDTO.setQuantity(variant.getQuantity());
        return variantDTO;
    }
}