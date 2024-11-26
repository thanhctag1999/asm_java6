package com.fpoly.java6.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Size;
import com.fpoly.java6.jpa.ColorJPA;
import com.fpoly.java6.jpa.ProductJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.ColorDto;
import com.fpoly.java6.model.dto.SizeDto;
@Service
public class ColorService {
	@Autowired
	VariantJPA variantJPA;
	
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	ColorJPA colorJPA;
	
	 public List<ColorDto> getAllColor() {
	        // Lấy tất cả các kích cỡ và chuyển đổi thành SizeDto
	        List<Color> color = colorJPA.findAll();
	        return color.stream().map(this::convertToDto).collect(Collectors.toList());
	    }

	    // Phương thức chuyển đổi từ Size entity sang SizeDto
	    private ColorDto convertToDto(Color color) {
	    	ColorDto colorDto = new ColorDto();
	    	colorDto.setId(color.getId());
	    	colorDto.setColor(color.getColor());
	        return colorDto;
	    }
}
