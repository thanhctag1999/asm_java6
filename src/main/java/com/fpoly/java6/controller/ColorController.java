package com.fpoly.java6.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.ColorJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.ColorDto;
import com.fpoly.java6.model.dto.SizeDto;
import com.fpoly.java6.service.ColorService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class ColorController {
	@Autowired
	ColorJPA colorJPA;
	@Autowired
	ColorService colorService;

	@GetMapping("/colors")
    public List<ColorDto> getAllColors() {
        return colorService.getAllColor();  // Lấy tất cả các kích cỡ dưới dạng SizeDto
    }
}
