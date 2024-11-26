package com.fpoly.java6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.entities.Size;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.SizeDto;
import com.fpoly.java6.service.SizeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class SizeController {
	@Autowired
	private SizeService sizeService;
	
	@GetMapping("/sizes")
    public List<SizeDto> getAllSizes() {
        return sizeService.getAllSizes();  // Lấy tất cả các kích cỡ dưới dạng SizeDto
    }
//
//	@GetMapping("/size")
//	public List<SizeDto> getAllSizes() {
//		return sizeService.getAllSizes();
//	}
//
//	@GetMapping("/sizes/{id}")
//    public List<ProductDto> getProductsBySizeId(@PathVariable int id) {
//        // Gọi service để lấy sản phẩm theo sizeId
//        return sizeService.getProductsBySizeId(id);
//    }
}
