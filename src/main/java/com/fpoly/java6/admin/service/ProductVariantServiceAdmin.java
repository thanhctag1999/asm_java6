package com.fpoly.java6.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.java6.admin.repository.ColorRepository;
import com.fpoly.java6.admin.repository.ProductRepository;
import com.fpoly.java6.admin.repository.SizeRepository;
import com.fpoly.java6.admin.repository.VariantRepository;
import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Size;
import com.fpoly.java6.entities.Variant;

import lombok.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantServiceAdmin {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;
    
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;
    
    @Autowired
    private ColorServiceAdmin colorService;
    
    @Autowired
    private SizeServiceAdmin sizeService;

	public List<Variant> getVariants(int id) {
        // Truy vấn các biến thể của sản phẩm từ cơ sở dữ liệu
        return variantRepository.findByProduct_Id(id);
    }
    

//    public Product findById(Long productId) {
//        return productRepository.findById(productId).orElse(null);
//    }

    public Product findById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null); // Make sure it returns null if not found
    }

   
    // Đường dẫn lưu ảnh trong thư mục static/images
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    // Lưu ảnh vào thư mục static/images và trả về URL hoặc đường dẫn của ảnh
    public String saveImage(MultipartFile image) throws IOException {
        // Đảm bảo thư mục tải lên tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục nếu không tồn tại
        }

        // Tạo tên tệp duy nhất (thời gian + tên tệp gốc)
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + image.getOriginalFilename();

        // Đường dẫn để lưu ảnh
        Path path = Paths.get(UPLOAD_DIR + fileName);

        try {
            // Sao chép byte của ảnh vào tệp
            Files.copy(image.getInputStream(), path);
        } catch (IOException e) {
            e.printStackTrace(); // In lỗi chi tiết để dễ dàng sửa lỗi
            throw new IOException("Error saving image: " + e.getMessage());
        }

        // Trả về đường dẫn tương đối (có thể dùng để phục vụ ảnh)
        return "/images/" + fileName;
    }

    // Kiểm tra mã vạch có tồn tại hay không
//    public boolean existsByBarcode(String barcode) {
//        return productRepository.existsByBarcode(barcode);  // Trả về true nếu mã vạch đã tồn tại
//    }

    // Phương thức thêm sản phẩm
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Variant addVariant(Integer productId, Integer colorId, Integer sizeId, Integer price, Integer quantity) {
        if (colorId == null || sizeId == null || price == null || quantity == null) {
            throw new IllegalArgumentException("All fields must be provided.");
        }

        // Tiến hành các bước tạo Variant, ví dụ tìm sản phẩm, màu sắc, size...
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Color color = colorService.findById(colorId).orElseThrow(() -> new IllegalArgumentException("Color not found"));
        Size size = sizeService.findById(sizeId).orElseThrow(() -> new IllegalArgumentException("Size not found"));

        // Tạo và lưu Variant
        Variant variant = new Variant();
        variant.setProduct(product);
        variant.setColor(color);
        variant.setSize(size);
        variant.setPrice(price);
        variant.setQuantity(quantity);

        return variantRepository.save(variant);
    }

    public boolean deleteVariant(int id) {
        Variant variant = variantRepository.findById(id);
        if (variant != null) {
            variantRepository.delete(variant);
            return true;
        }
        return false;
    }


    // Phương thức xóa sản phẩm và các biến thể của nó
    public boolean deleteProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            // Xóa các biến thể liên quan trước khi xóa sản phẩm
            variantRepository.deleteByProductId(id);  // Đảm bảo rằng phương thức này được định nghĩa trong VariantRepository
            productRepository.deleteById(id);  // Xóa sản phẩm
            return true;
        }
        return false;  // Nếu sản phẩm không tồn tại
    }

    
    public boolean existsByProductIdAndColorIdAndSizeId(int productId, int colorId, int sizeId) {
        return variantRepository.existsByProduct_IdAndColor_IdAndSize_Id(productId, colorId, sizeId);
    }
    
    public List<Product> getAllProducts() {
        // Truy vấn tất cả sản phẩm từ cơ sở dữ liệu
        return productRepository.findAll();
    }

    public List<Variant> getAllVariants() {
        // Truy vấn tất cả các biến thể từ cơ sở dữ liệu
        return variantRepository.findAll();
    }


    public void deleteProductsByIds(List<Integer> productIds) {
        for (int id : productIds) {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
            } else {
                throw new RuntimeException("Sản phẩm không tồn tại: " + id);
            }
        }
    }
}
