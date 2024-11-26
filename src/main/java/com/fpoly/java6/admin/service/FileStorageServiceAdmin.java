package com.fpoly.java6.admin.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceAdmin {

    private final String uploadDir = "src/main/resources/static/images";

    public String storeFile(MultipartFile file) throws IOException {
        Path uploadPath = getUploadPath();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/images/" + fileName;
    }

//    public String storeFileFromUrl(String imageUrl) throws IOException {
//        Path uploadPath = getUploadPath();
//        String fileName = System.currentTimeMillis() + "_" + Paths.get(new URL(imageUrl).getPath()).getFileName();
//        Path filePath = uploadPath.resolve(fileName);
//
//        try (InputStream in = new URL(imageUrl).openStream()) {
//            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
//        }
//        
//        return "/images/" + fileName+".png";
//    }

    private Path getUploadPath() throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        return uploadPath;
    }
    
    public String storeFileFromUrl(String imageUrl) throws IOException { 
        // Kiểm tra nếu URL là một URL đầy đủ hoặc chỉ là đường dẫn file
        if (imageUrl.startsWith("/images/")) {
            // Nếu URL chỉ chứa đường dẫn hình ảnh (từ cơ sở dữ liệu)
            return imageUrl;
        }

        // Kiểm tra nếu URL không có giao thức (protocol)
        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            throw new MalformedURLException("URL must start with http:// or https://");
        }

        // Kiểm tra xem URL có phải là một ảnh hợp lệ không
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        connection.connect();

        // Lấy Content-Type từ header để kiểm tra xem nó có phải là ảnh không
        String contentType = connection.getContentType();
        
        // Nếu không phải ảnh, lưu trực tiếp URL vào cơ sở dữ liệu
        if (!contentType.startsWith("image/")) {
            // Thực hiện lưu URL vào cơ sở dữ liệu (có thể là trả về URL này cho việc lưu)
            return imageUrl;
        }

        // Tiến hành tải và lưu ảnh vào thư mục nếu là ảnh hợp lệ
        Path uploadPath = getUploadPath();
        String fileName = System.currentTimeMillis() + "_" + Paths.get(url.getPath()).getFileName();
        
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("URL does not contain a valid file name");
        }
        
        Path filePath = uploadPath.resolve(fileName);

        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Trả về đường dẫn đến ảnh đã lưu
        return "/images/" + fileName;
    }

}
