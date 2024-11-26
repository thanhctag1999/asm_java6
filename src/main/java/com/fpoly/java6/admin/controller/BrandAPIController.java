package com.fpoly.java6.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.fpoly.java6.admin.service.BrandServiceAdmin;
import com.fpoly.java6.admin.service.FileStorageServiceAdmin;
import com.fpoly.java6.entities.Brand;


@RestController
@RequestMapping("/Admin")
public class BrandAPIController {


	@Autowired
	private FileStorageServiceAdmin fileStorageService;

	@Autowired
	private BrandServiceAdmin brandService;

	
	@PostMapping("/brands/add")
	public ResponseEntity<?> addBrand(
	        @RequestParam("brandName") String brandName,
	        @RequestParam(value = "image", required = false) MultipartFile image,
	        @RequestParam(value = "imageUrl", required = false) String imageUrl) {

	    // Kiểm tra tên thương hiệu không được rỗng và hợp lệ
	    if (brandName == null || brandName.trim().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên thương hiệu không được để trống");
	    }
	    if (brandName.length() < 3 || brandName.length() > 100) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Tên thương hiệu phải có ít nhất 3 ký tự và không quá 100 ký tự");
	    }
	    if (!brandName.matches("[a-zA-Z0-9\\s]+")) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên thương hiệu không được chứa ký tự đặc biệt");
	    }

	    if (image != null && !image.isEmpty()) {
	        String fileType = image.getContentType();
	        if (!fileType.startsWith("image/")) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tệp tải lên phải là hình ảnh");
	        }
	    }

	    try {
	        // Tạo đối tượng Brand
	        Brand brand = new Brand();
	        brand.setName(brandName);

	        // Xử lý ảnh nếu có
	        if (image != null && !image.isEmpty()) {
	            String imagePath = fileStorageService.storeFile(image);
	            brand.setImage(imageUrl);
	        } else if (imageUrl != null && !imageUrl.isEmpty()) {
	            String imagePath = fileStorageService.storeFileFromUrl(imageUrl);
	            brand.setImage(imagePath);
	        }

	        // Lưu thương hiệu vào cơ sở dữ liệu
	        Brand savedBrand = brandService.save(brand);

			return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm thương hiệu: " + e.getMessage());
	    }
	}
	
	@PutMapping("/brands/edit/{id}")
	public ResponseEntity<?> updateBrand(@PathVariable int id,
	                                     @RequestParam("brandName") String brandName,
	                                     @RequestParam(value = "image", required = false) MultipartFile image,
	                                     @RequestParam(value = "imageUrl", required = false) String imageUrl) {

	    try {
	        // Tìm thương hiệu theo ID
	        Brand existingBrand = brandService.findById(id);
	        if (existingBrand == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Thương hiệu không tồn tại");
	        }

	        // Cập nhật tên thương hiệu nếu có
	        if (brandName != null && !brandName.trim().isEmpty()) {
	            brandName = brandName.trim(); // Loại bỏ khoảng trắng thừa
	            if (brandName.length() < 3 || brandName.length() > 100) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Tên thương hiệu phải có ít nhất 3 ký tự và không quá 100 ký tự");
	            }
	            if (!brandName.matches("[a-zA-Z0-9\\s]+")) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Tên thương hiệu không được chứa ký tự đặc biệt");
	            }
	            existingBrand.setName(brandName);
	        }

	        // Cập nhật hình ảnh nếu có
	        if (image != null && !image.isEmpty()) {
	            String imagePath = fileStorageService.storeFile(image);  // Lưu ảnh mới
	            existingBrand.setImage(imagePath);
	        } else if (imageUrl != null && !imageUrl.isEmpty()) {
	            existingBrand.setImage(imageUrl);  // Sử dụng URL nếu có
	        }

	        // Lưu thương hiệu đã cập nhật
	        Brand savedBrand = brandService.save(existingBrand);
	        return ResponseEntity.ok(savedBrand);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Lỗi khi cập nhật thương hiệu: " + e.getMessage());
	    }
	}

    @GetMapping(value = "/brands/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Brand> get(@PathVariable int id) {
        Brand brand = brandService.findById(id);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/brands/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable int id) {
        try {
            boolean isDeleted = brandService.deleteBrand(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Thương hiệu đã được xóa");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Thương hiệu không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa thương hiệu: " + e.getMessage());
        }
    }

    
}