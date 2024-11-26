package com.fpoly.java6.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Brand;
import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Size;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.BrandJPA;
import com.fpoly.java6.jpa.ColorJPA;
import com.fpoly.java6.jpa.ProductJPA;
import com.fpoly.java6.jpa.SizeJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.BrandWithProductsDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.SearchDto;
import com.fpoly.java6.model.dto.TypeWithProductsDto;
import com.fpoly.java6.model.mapper.ProductMapper;
import com.fpoly.java6.model.mapper.SearchMapper;

@Service
public class ProductService {

	@Autowired
	private ProductJPA productJPA;

	@Autowired
	private VariantJPA variantJPA;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private SizeJPA sizeJPA;

	@Autowired
	private BrandJPA brandJPA;

	@Autowired
	private ColorJPA colorJPA;

	public List<Product> findAllProducts() {
//	        Pageable pageable = PageRequest.of(page, size);  // Tạo Pageable cho phân trang
		return productJPA.findAll(); // Lấy dữ liệu phân trang từ repository
	}

	public Product getProductById(int id) {
		return productJPA.findById(id).orElse(null);
	}

	public List<Variant> getVariantsByProductId(int productId) {
		return variantJPA.findByProductId(productId);
	}

	public List<Size> getSizes() {
		return sizeJPA.findAll();
	}

	public List<Color> getColors() {
		return colorJPA.findAll();
	}

	public List<ProductDto> getProductsByBrand(String brandName) {
		// Lấy danh sách sản phẩm theo tên thương hiệu và chuyển sang ProductDto
		return productJPA.findByBrandName(brandName).stream().map(ProductMapper::tProductDto)
				.collect(Collectors.toList());
	}

	public List<ProductDto> getProductByType(String typeName) {
		// Lấy danh sách sản phẩm theo tên thương hiệu và chuyển sang ProductDto
		return productJPA.findByTypeName(typeName).stream().map(ProductMapper::tProductDto)
				.collect(Collectors.toList());
	}

	public List<ProductDto> getAllProduct() {
		// Lấy tất cả sản phẩm và chuyển đổi sang ProductDto
		List<ProductDto> products = productJPA.findAll().stream().map(ProductMapper::tProductDto)
				.collect(Collectors.toList());

		// Nhóm sản phẩm theo brandName
		Map<String, List<ProductDto>> groupedByBrand = products.stream()
				.collect(Collectors.groupingBy(ProductDto::getBrandName));

		return products;
	}

	public List<ProductDto> getRelatedProductsByBrand(String brand) {
		List<Product> products = productJPA.findByBrandName(brand);

		// Chuyển đổi các đối tượng Product thành ProductDto (DTO để trả về)
		return products.stream().map(ProductMapper::tProductDto) // Static method call
				.collect(Collectors.toList());
	}

	public List<BrandWithProductsDto> getAllProductsGroupedByBrand() {
		List<ProductDto> products = getAllProduct(); // Lấy tất cả sản phẩm

		// Nhóm sản phẩm theo brandName
		Map<String, List<ProductDto>> productsByBrand = products.stream()
				.collect(Collectors.groupingBy(ProductDto::getBrandName));

		// Chuyển đổi Map thành danh sách BrandWithProductsDto
		return productsByBrand.entrySet().stream()
				.map(entry -> new BrandWithProductsDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());
	}

	public List<TypeWithProductsDto> getAllProductsGroupedByType() {
		List<ProductDto> products = getAllProduct(); // Lấy tất cả sản phẩm

		// Nhóm sản phẩm theo brandName
		Map<String, List<ProductDto>> productsByType = products.stream()
				.collect(Collectors.groupingBy(ProductDto::getTypeName));

		// Chuyển đổi Map thành danh sách BrandWithProductsDto
		return productsByType.entrySet().stream()
				.map(entry -> new TypeWithProductsDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());
	}

	public List<SearchDto> searchProducts(String keyword) {
		return productJPA.searchByName(keyword).stream().map(SearchMapper::tSearchDto) // Use the original method name
				.collect(Collectors.toList());
	}

	public List<Product> getProductsBySize(int sizeId) {
		return productJPA.findBySizeId(sizeId);
	}

	public List<Product> getProductsByColor(String colorId) {
		return productJPA.findByColorId(colorId);
	}

}
