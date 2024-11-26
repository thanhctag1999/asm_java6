package com.fpoly.java6.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.VariantDto;
import com.fpoly.java6.service.VariantService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class VariantController {
	@Autowired
	VariantJPA variantJPA;
	@Autowired
	VariantService variantService;

//	@GetMapping("/sort-variants")
//	public List<VariantDto> getAllVariants(@RequestParam(required = false, defaultValue = "asc") String sortOrder) {
//		// Gọi phương thức trong service để lấy tất cả các variants đã được sắp xếp theo
//		// giá
//		return variantService.getAllVariants(sortOrder);
//	}
}
