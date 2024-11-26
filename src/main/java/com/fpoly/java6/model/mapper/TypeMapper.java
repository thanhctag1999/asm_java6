package com.fpoly.java6.model.mapper;

import org.springframework.stereotype.Component;
import com.fpoly.java6.entities.Type;
import com.fpoly.java6.model.dto.TypeDto;

@Component
public class TypeMapper {
	public static TypeDto typeDto(Type type) {
		TypeDto typeDto = new TypeDto();
		typeDto.setId(type.getId());
		typeDto.setName(type.getName());
		typeDto.setProduct(type.getProduct());
        return typeDto;
    }
}

