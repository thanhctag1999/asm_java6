package com.fpoly.java6.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.model.dto.BrandDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.VariantDto;

@Component
public class ProductMapper {

	public static ProductDto tProductDto(Product product) {
        ProductDto productDTO = new ProductDto();
        
        // Set các thuộc tính cơ bản của sản phẩm
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setDate(product.getDate());
        productDTO.setImage(product.getImage());
        productDTO.setStatus(product.getStatus());

        // Chuyển danh sách các variant thành VariantDTO
        List<VariantDto> variantDTOs = product.getVariants().stream()
                                               .map(VariantMapper::tVariantDto)  // Chuyển đổi từng variant
                                               .collect(Collectors.toList());
        productDTO.setVariants(variantDTOs);

        // Chuyển đổi thông tin thương hiệu
        BrandDto brandDto = new BrandDto(); 
        brandDto.setName(product.getBrand().getName());  // Lấy tên thương hiệu
        productDTO.setBrandName(brandDto.getName());  // Set tên thương hiệu vào DTO

        // Set tên loại sản phẩm nếu có
        productDTO.setTypeName(product.getType() != null ? product.getType().getName() : null);

        return productDTO;
    }
}
