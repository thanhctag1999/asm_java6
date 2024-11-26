 package com.fpoly.java6.admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.java6.admin.service.AccountServiceAdmin;
import com.fpoly.java6.admin.service.FileStorageServiceAdmin;
import com.fpoly.java6.entities.Account;

@RestController
@RequestMapping("/Admin")
public class AccountAPIController {

	@Autowired
	private FileStorageServiceAdmin fileStorageService;
	
    @Autowired
    private AccountServiceAdmin accountService;

    @PostMapping("/accounts/add")
    public ResponseEntity<?> addAccount(@RequestParam("fullName") String fullName,
                                        @RequestParam("email") String email,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("gender") String gender,
                                        @RequestParam("password") String password,
                                        @RequestParam(value = "image", required = false) MultipartFile image,
                                        @RequestParam(value = "imageUrl", required = false) String imageUrl) {

        if (fullName == null || fullName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fullName");
        }
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email");
        }
        if (phone == null || phone.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("phone");
        }

        if (accountService.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email");
        }
        if (accountService.existsByPhone(phone)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("phone");
        }

        if (password == null || password.trim().isEmpty()) {
            password = "123456";
        }

        // Mã hóa mật khẩu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        // Create and save the account
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Account account = new Account();
            account.setName(fullName);
            account.setEmail(email);
            account.setPhone(phone);
            account.setRole(2);
             // Gán giá trị vào account            account.setRole(role);
            account.setGender(true);
            account.setPassword(hashedPassword);  // Consider hashing the password
            if (image != null && !image.isEmpty()) {
                String imagePath = fileStorageService.storeFile(image);
                account.setImage(imagePath);
            } else if (imageUrl != null && !imageUrl.isEmpty()) {
                String imagePath = fileStorageService.storeFileFromUrl(imageUrl);
                account.setImage(imagePath);
            }

            account.setActived(true);

            Account savedAccount = accountService.saveAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("createAccount: " + e.getMessage());
        }
    }
    

    @PutMapping("/accounts/edit/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("gender") Boolean gender,
            @RequestParam("role") int role,
            @RequestParam("password") String password,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "imageUrl", required = false) String imageUrl) {

        try {
            // Tìm tài khoản hiện tại
            Account existingAccount = accountService.findById(id);
            if (existingAccount == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id");
            }

            boolean isUpdated = false;

            // Kiểm tra và cập nhật thông tin tài khoản chỉ khi có sự thay đổi
            if (fullName != null && !fullName.equals(existingAccount.getName())) {
                existingAccount.setName(fullName);
                isUpdated = true;
            }

            if (email != null && !email.equals(existingAccount.getEmail())) {
                // Kiểm tra email đã tồn tại chưa (trừ tài khoản hiện tại)
                Account accountWithEmail = accountService.findByEmail(email);
                if (accountWithEmail != null && accountWithEmail.getId() == (existingAccount.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email");
                }
                existingAccount.setEmail(email);
                isUpdated = true;
            }

            if (phone != null && !phone.equals(existingAccount.getPhone())) {
                Account accountWithPhone = accountService.findByPhone(phone);
                if (accountWithPhone != null && accountWithPhone.getId() == (existingAccount.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("phone");
                }
                existingAccount.setPhone(phone);
                isUpdated = true;
            }


            if (gender != null) {
                existingAccount.setGender(true);
                isUpdated = true;
            }

            existingAccount.setRole(role);
            isUpdated = true;
           

            if (password != null && !password.isEmpty() && !password.equals(existingAccount.getPassword())) {
                if (password.length() < 6) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Mật khẩu phải có ít nhất 6 ký tự\"}");
                }
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(password);
                existingAccount.setPassword(encodedPassword); // Mã hóa mật khẩu
                isUpdated = true;
            }

            // Cập nhật ảnh nếu có thay đổi
            if (image != null && !image.isEmpty()) {
                String imagePath = fileStorageService.storeFile(image);
                if (!imagePath.equals(existingAccount.getImage())) { // Kiểm tra nếu có sự thay đổi ảnh
                    existingAccount.setImage(imagePath);
                    isUpdated = true;
                }
            } else if (imageUrl != null && !imageUrl.isEmpty()) {
                String imagePath = fileStorageService.storeFileFromUrl(imageUrl);
                if (!imagePath.equals(existingAccount.getImage())) { // Kiểm tra nếu có sự thay đổi ảnh
                    existingAccount.setImage(imagePath);
                    isUpdated = true;
                }
            }

            // Nếu có thay đổi thì lưu lại
            if (isUpdated) {
                accountService.saveAccount(existingAccount);
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Cập nhật thành công\"}");
            }

            // Trả về HTTP 200 OK mà không có thông báo nếu không có thay đổi
            return ResponseEntity.status(HttpStatus.OK).body("{}"); // Trả về một đối tượng JSON trống

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Lỗi khi cập nhật tài khoản: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping(value = "/accounts/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        Account account = accountService.findById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/accounts/delete/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable int accountId) {
        boolean isDeleted = accountService.deleteAccount(accountId);
        if (isDeleted) {
            return ResponseEntity.ok().body("Tài khoản đã được xóa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại");
        }
    }
  
}
