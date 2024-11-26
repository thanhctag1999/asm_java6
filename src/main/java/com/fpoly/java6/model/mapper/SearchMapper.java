package com.fpoly.java6.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.model.dto.BrandDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.SearchDto;
import com.fpoly.java6.model.dto.VariantDto;

@Component
public class SearchMapper {

    // This method maps Product to SearchDto
    public static SearchDto tSearchDto(Product product) {
        SearchDto searchDto = new SearchDto();

        // Set các thuộc tính cơ bản của sản phẩm
        searchDto.setId(product.getId());
        searchDto.setName(product.getName());
        searchDto.setDescription(product.getDescription());
        searchDto.setDate(product.getDate());
        searchDto.setImage(product.getImage());
        searchDto.setStatus(product.getStatus());

        // Chuyển danh sách các variant thành VariantDTO
        List<VariantDto> variantDTOs = product.getVariants().stream()
                                               .map(VariantMapper::tVariantDto)  // Chuyển đổi từng variant
                                               .collect(Collectors.toList());
        searchDto.setVariants(variantDTOs);

        // Chuyển đổi thông tin thương hiệu
        searchDto.setBrandName(product.getBrand().getName()); // Directly set brand name here

        // Set tên loại sản phẩm nếu có
        searchDto.setTypeName(product.getType() != null ? product.getType().getName() : null);

        return searchDto;
    }
}
