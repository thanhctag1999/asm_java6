package com.fpoly.java6.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Size;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.ProductJPA;
import com.fpoly.java6.jpa.SizeJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.SizeDto;
import com.fpoly.java6.model.dto.VariantDto;
import com.fpoly.java6.model.mapper.SizeMapper;

@Service
public class SizeService {
	@Autowired
	SizeJPA sizeJPA;
	
	@Autowired
	VariantJPA variantJPA;
	
	@Autowired
	ProductJPA productJPA;

	 public List<SizeDto> getAllSizes() {
	        // Lấy tất cả các kích cỡ và chuyển đổi thành SizeDto
	        List<Size> sizes = sizeJPA.findAll();
	        return sizes.stream().map(this::convertToDto).collect(Collectors.toList());
	    }

	    // Phương thức chuyển đổi từ Size entity sang SizeDto
	    private SizeDto convertToDto(Size size) {
	        SizeDto sizeDto = new SizeDto();
	        sizeDto.setId(size.getId());
	        sizeDto.setSize(size.getSize());
	        return sizeDto;
	    }

}
