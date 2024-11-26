package com.fpoly.java6.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.model.dto.BrandWithProductsDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.SearchDto;
import com.fpoly.java6.model.dto.TypeWithProductsDto;
import com.fpoly.java6.model.dto.VariantDto;
import com.fpoly.java6.model.mapper.ProductMapper;
import com.fpoly.java6.model.mapper.VariantMapper;
import com.fpoly.java6.service.ProductService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080") 
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProductsByBrand2(
            @RequestParam(value = "brandName", required = false) String brandName) {
        
        List<ProductDto> products;
        
        if (brandName == null || brandName.isEmpty()) {
            // Nếu không có brandName, lấy tất cả sản phẩm
            products = productService.getAllProduct();
        } else {
            // Nếu có brandName, lấy sản phẩm theo thương hiệu
            products = productService.getProductsByBrand(brandName);
        }
        
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();  // Trả về 204 nếu không có sản phẩm
        }
        
        return ResponseEntity.ok(products);  // Trả về danh sách sản phẩm
    }
	
    @GetMapping("/brands")
    public ResponseEntity<List<BrandWithProductsDto>> getAllProductsGroupedByBrand() {
        List<BrandWithProductsDto> brandsWithProducts = productService.getAllProductsGroupedByBrand();
        if (brandsWithProducts.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có sản phẩm nào
        }	
        return ResponseEntity.ok(brandsWithProducts); // Trả về 200 cùng danh sách sản phẩm đã nhóm
    }

    @GetMapping("/types")
    public ResponseEntity<List<TypeWithProductsDto>> gettAllProductsGroupedByType() {
        List<TypeWithProductsDto> typesWithProducts = productService.getAllProductsGroupedByType();
        if (typesWithProducts.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có sản phẩm nào
        }	
        return ResponseEntity.ok(typesWithProducts); // Trả về 200 cùng danh sách sản phẩm đã nhóm
    }
    @GetMapping("/types/{typeName}")
    public ResponseEntity<List<ProductDto>> getProductsByType(@PathVariable String typeName) {
        List<ProductDto> products = productService.getProductByType(typeName);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
    

    @GetMapping("/brands/{brandName}")
    public ResponseEntity<List<ProductDto>> getProductsByBrand(@PathVariable String brandName) {
        List<ProductDto> products = productService.getProductsByBrand(brandName);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
  
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build(); 
        }

        // Convert Product entity to ProductDto
        ProductDto productDto = ProductMapper.tProductDto(product);

        return ResponseEntity.ok(productDto); 
    }
    
    @GetMapping("/products/by-brand")
    public List<ProductDto> getRelatedProductsByBrand(@RequestParam String brand) {
        return productService.getRelatedProductsByBrand(brand); // Call the service method
    }
    

    @GetMapping("/variants")
    public ResponseEntity<List<VariantDto>> getVariants(@RequestParam int productId) {
        List<Variant> variants = productService.getVariantsByProductId(productId);
        if (variants.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 if no variants found
        }

        // Convert Variant entities to VariantDto
        List<VariantDto> variantDtos = variants.stream()
                .map(VariantMapper::tVariantDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(variantDtos);  // Return 200 with the list of VariantDto
    }

    @GetMapping("/size/{sizeId}")
    public List<ProductDto> getProductsBySize(@PathVariable int sizeId) {
        List<Product> products = productService.getProductsBySize(sizeId);
        // Convert Product entities to ProductDto (using a DTO mapper)
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        return dto;
    }

    @GetMapping("/color/{colorId}")
    public List<ProductDto> getProductsByColor(@PathVariable String colorId) {
        List<Product> products = productService.getProductsByColor(colorId);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    
  
//    @GetMapping("/colors")
//    public ResponseEntity<List<String>> getColors() {
//        List<String> colors = productService.getColors().stream()
//                .map(color -> color.getColor())  // Assuming Color entity has a 'getName()' method
//                .collect(Collectors.toList());
//        if (colors.isEmpty()) {
//            return ResponseEntity.noContent().build();  // Return 204 if no colors found
//        }
//        return ResponseEntity.ok(colors);  // Return 200 with list of colors
//    }
    
    @GetMapping("/search")
    @ResponseBody
    public List<SearchDto> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
}
