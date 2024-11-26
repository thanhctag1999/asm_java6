package com.fpoly.java6.admin.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.fpoly.java6.admin.repository.SizeRepository;
import com.fpoly.java6.admin.service.BrandServiceAdmin;
import com.fpoly.java6.admin.service.ColorServiceAdmin;
import com.fpoly.java6.admin.service.FileStorageServiceAdmin;
import com.fpoly.java6.admin.service.ProductVariantServiceAdmin;
import com.fpoly.java6.admin.service.SizeServiceAdmin;
import com.fpoly.java6.admin.service.TypeServiceAdmin;
import com.fpoly.java6.entities.Brand;
import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Size;
import com.fpoly.java6.entities.Type;
import com.fpoly.java6.entities.Variant;




@RestController
@RequestMapping("/Admin")
public class ProductAPIController {


	@Autowired
	private FileStorageServiceAdmin fileStorageService;

	@Autowired
	private BrandServiceAdmin brandService;

	@Autowired
	private TypeServiceAdmin typeService;

	@Autowired
	private ColorServiceAdmin colorService;

	@Autowired
	private SizeServiceAdmin sizeService;

	@Autowired
	private ProductVariantServiceAdmin productVariantService;
	
	@Autowired
    private SizeRepository sizeRepository;

	@PostMapping("/products/add")
	public ResponseEntity<?> addProduct(@RequestParam("productName") String productName,
			@RequestParam("barcode") String barcode, @RequestParam("description") String description,
			@RequestParam("status") String status, @RequestParam("brandId") int brandId,
			@RequestParam("typeId") int typeId, @RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "imageUrl", required = false) String imageUrl) {

		if (productName == null || productName.trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("productName");
		}
		if (productName.length() < 3 || productName.length() > 200) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("productName");
		}
		if (!productName.matches("[a-zA-Z0-9\\s]+")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("productName");
		}

		if (barcode == null || barcode.trim().isEmpty() || !barcode.matches("[0-9]+")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("barcode");
		}

		if (barcode.length() < 6 || barcode.length() > 9) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("barcode");
		}

		if (brandService.findById(brandId) == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("brand");
		}
		if (typeService.findById(typeId) == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("type");
		}

		if (image != null && !image.isEmpty()) {
			String fileType = image.getContentType();
			if (!fileType.startsWith("image/")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("image");
			}
		}
		

		try {
			Brand brand = brandService.findById(brandId);
			Type type = typeService.findById(typeId);

			Product product = new Product();
			product.setName(productName);
//			product.setBarcode(barcode);
			product.setDescription(description);
			product.setStatus(1);
			product.setBrand(brand); // Gán brand vào sản phẩm
			product.setType(type); // Gán type vào sản phẩm

			if (image != null && !image.isEmpty()) {
			    String imagePath = fileStorageService.storeFile(image);
			    product.setImage(imagePath);
			} else if (imageUrl != null && !imageUrl.isEmpty()) {
			    String imagePath = fileStorageService.storeFileFromUrl(imageUrl);
			    product.setImage(imagePath);
			}

			Product savedProduct = productVariantService.saveProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("createProduct: " + e.getMessage());
		}
	}
	
	@PutMapping("/products/edit/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable int id,
	                                       @RequestParam("productName") String productName,
	                                       @RequestParam("barcode") String barcode,
	                                       @RequestParam("description") String description,
	                                       @RequestParam("status") int status,
	                                       @RequestParam("brandId") int brandId,
	                                       @RequestParam("typeId") int typeId,
	                                       @RequestParam(value = "image", required = false) MultipartFile image,
	                                       @RequestParam(value = "imageUrl", required = false) String imageUrl) {
	    try {
	        Product existingProduct = productVariantService.findById(id);
	        if (existingProduct == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại");
	        }

	        existingProduct.setName(productName);
//	        existingProduct.setBarcode(barcode);
	        existingProduct.setDescription(description);
	        existingProduct.setStatus(status);

	        Brand brand = brandService.findById(brandId);
	        if (brand == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Brand");
	        }
	        existingProduct.setBrand(brand);

	        Type type = typeService.findById(typeId);
	        if (type == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Type");
	        }
	        existingProduct.setType(type);

	        if (image != null && !image.isEmpty()) {
	            String imagePath = fileStorageService.storeFile(image); // Lưu tệp ảnh
	            existingProduct.setImage(imagePath);
	        } else if (imageUrl != null && !imageUrl.isEmpty()) {
	            String imagePath = fileStorageService.storeFileFromUrl(imageUrl); // Lưu ảnh từ URL
	            existingProduct.setImage(imagePath);
	        }

	        Product updatedProduct = productVariantService.saveProduct(existingProduct);
	        return ResponseEntity.ok(updatedProduct);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
	    }
	}


	    @GetMapping(value = "/products/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Product> getProductById(@PathVariable int id) {
	        Product product = productVariantService.findById(id);
	        if (product != null) {
	            return ResponseEntity.ok(product);
	        }
	        return ResponseEntity.notFound().build();
	    }

	@PostMapping("/{productId}/variants")
	public ResponseEntity<?> addVariant(@PathVariable int productId, @RequestParam("colorId") int colorId,
	        @RequestParam("sizeId") int sizeId, @RequestParam("price") int price,
	        @RequestParam("quantity") int quantity) {

	    try {
	        boolean variantExists = productVariantService.existsByProductIdAndColorIdAndSizeId(productId, colorId, sizeId);
	        if (variantExists) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("Sản phẩm đã có biến thể với màu sắc và kích thước này.");
	        }

	        Variant savedVariant = productVariantService.addVariant(productId, colorId, sizeId, price, quantity);

	        Color color = colorService.findByIdOrNull(colorId);
	        Size size = sizeService.findByIdOrNull(sizeId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("id", savedVariant.getId());
	        response.put("colorName", color.getColor());
	        response.put("sizeName", size.getSize());
	        response.put("price", savedVariant.getPrice());
	        response.put("quantity", savedVariant.getQuantity());

	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	    }
	}
	

	@GetMapping("/{productId}/variants")
	public ResponseEntity<List<Variant>> getVariants(@PathVariable int productId) {
		List<Variant> variants = productVariantService.getVariants(productId);
		return ResponseEntity.ok(variants);
	}

	@DeleteMapping("/variants/{id}")
	public ResponseEntity<Void> deleteVariant(@PathVariable int id) {
		boolean isDeleted = productVariantService.deleteVariant(id);
		if (isDeleted) {
			return ResponseEntity.ok().build(); // Trả về 200 OK nếu xóa thành công
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
		}
	}
	
	@DeleteMapping("/products/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
	    boolean isDeleted = productVariantService.deleteProduct(productId);
	    if (isDeleted) {
	        return ResponseEntity.ok().body("Sản phẩm đã được xóa");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại");
	    }
	}

}
