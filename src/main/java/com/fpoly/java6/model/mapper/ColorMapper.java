package com.fpoly.java6.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fpoly.java6.entities.Color;
import com.fpoly.java6.model.dto.ColorDto;
import com.fpoly.java6.model.dto.VariantDto;

@Component
public class ColorMapper {
	public static ColorDto tColorDto(Color color) {
		ColorDto colorDto = new ColorDto();
		colorDto.setId(color.getId());
		colorDto.setColor(color.getColor());
		List<VariantDto> variantDTOs = color.getVariants().stream()
                .map(VariantMapper::tVariantDto)  // Chuyển đổi từng variant
                .collect(Collectors.toList());
		return colorDto;
	}
}
