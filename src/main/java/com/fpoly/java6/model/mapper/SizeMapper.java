package com.fpoly.java6.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fpoly.java6.entities.Size;
import com.fpoly.java6.model.dto.SizeDto;
import com.fpoly.java6.model.dto.VariantDto;

@Component
public class SizeMapper {
	public static SizeDto tSizeDto(Size size) {
		SizeDto sizeDto = new SizeDto();
		sizeDto.setId(size.getId());
		sizeDto.setSize(size.getSize());
		List<VariantDto> variantDTOs = size.getVariants().stream()
                .map(VariantMapper::tVariantDto)  // Chuyển đổi từng variant
                .collect(Collectors.toList());
		return sizeDto;
	}
}
