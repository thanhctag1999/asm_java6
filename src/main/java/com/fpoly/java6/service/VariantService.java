package com.fpoly.java6.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Size;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.SizeJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.SizeDto;
import com.fpoly.java6.model.dto.VariantDto;

import com.fpoly.java6.model.mapper.VariantMapper;

@Service
public class VariantService {
	@Autowired
	VariantJPA variantJPA;
	@Autowired
	private SizeJPA sizeJPA;
	
//    public List<Variant> getVariantsByPrice(int minPrice, int maxPrice, String sortOrder) {
//        Sort sort = "desc".equalsIgnoreCase(sortOrder) ? 
//                    Sort.by(Sort.Order.desc("price")) : 
//                    Sort.by(Sort.Order.asc("price"));
//        return variantJPA.findByPrice(minPrice, maxPrice, sort);
//    }
	
	 public List<VariantDto> getAllVariants(String sortOrder) {
	        List<Variant> variants = variantJPA.findAll();
	        List<VariantDto> variantDtos = variants.stream()
	                .map(VariantMapper::tVariantDto)
	                .collect(Collectors.toList());
	        
	        if ("asc".equalsIgnoreCase(sortOrder)) {
	            // Sắp xếp từ thấp đến cao
	            variantDtos.sort((v1, v2) -> Integer.compare(v1.getPrice(), v2.getPrice()));
	        } else if ("desc".equalsIgnoreCase(sortOrder)) {
	            // Sắp xếp từ cao đến thấp
	            variantDtos.sort((v1, v2) -> Integer.compare(v2.getPrice(), v1.getPrice()));
	        }
	        return variantDtos;
	    }
	 
	  
	
//	  public List<VariantDto> sortVariantsByPrice(List<VariantDto> variants) {
//	        // Sắp xếp theo giá từ thấp đến cao
//	        variants.sort(Comparator.comparingInt(VariantDto::getPrice));
//	        return variants;
//	    }
//	
//	  public List<VariantDto> sortVariantsByPriceDescending(List<VariantDto> variants) {
//	        // Sắp xếp theo giá từ cao đến thấp
//	        variants.sort((v1, v2) -> Integer.compare(v2.getPrice(), v1.getPrice()));
//	        return variants;
//	    }
}
