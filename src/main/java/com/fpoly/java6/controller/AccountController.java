package com.fpoly.java6.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.jpa.AccountJPA;
import com.fpoly.java6.model.dto.AccountDTO;
import com.fpoly.java6.model.dto.AddressDTO;
import com.fpoly.java6.model.dto.ApiResponse;
import com.fpoly.java6.model.dto.ForgotPasswordRequest;
import com.fpoly.java6.model.dto.LoginRequest;
import com.fpoly.java6.model.dto.RegisterRequest;
import com.fpoly.java6.model.dto.ResetPasswordRequest;
import com.fpoly.java6.model.mapper.AddressMapper;
import com.fpoly.java6.service.AccountService;
import com.fpoly.java6.service.MailService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	HttpServletResponse response;
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	AccountJPA accountJPA;
	
	@Autowired
	private MailService emailService;
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public String generateResetToken() {
	    return UUID.randomUUID().toString(); 
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
	    String email = loginRequest.getEmail();

	    Account account = accountService.findByEmail(email).orElse(null);
	    if (account == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ApiResponse("error", "Email not found"));
	    }
	    
	    boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), account.getPassword());
	    
	    if (!passwordMatch) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ApiResponse("error", "Invalid password"));
	    }

	    // Save cookie
	    Cookie cookie = new Cookie("user", account.getId() + "-" + account.getRole());
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
	    cookie.setPath("/");
	    response.addCookie(cookie);

	    // Convert `Address` to `AddressDTO`
	    List<AddressDTO> addressDTOs = account.getAddresses().stream()
	            .map(AddressMapper::toDTO)
	            .toList();

	    // Convert `Account` to `AccountDTO`
	    AccountDTO accountDTO = new AccountDTO(
	        account.getId(),
	        account.getName(),
	        account.getPhone(),
	        account.isGender(),
	        account.getEmail(),
	        account.getImage(),
	        account.getRole(),
	        account.isActived(),
	        addressDTOs
	    );

	    return ResponseEntity.ok(accountDTO);
	}

	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // Check if the email already exists
        if (accountJPA.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("error", "Email already exists"));
        }
        
        // Create a new account
        Account newAccount = new Account();
        newAccount.setName(registerRequest.getName());
        newAccount.setEmail(registerRequest.getEmail());
        newAccount.setPassword(registerRequest.getPassword()); // Hash password
        newAccount.setRole(2); // Default role
        newAccount.setActived(true); // Default active state

        // Save new account
        accountService.save(newAccount);

        // Return success as JSON
        return ResponseEntity.status(HttpStatus.CREATED)
             .body(new ApiResponse("success", "Account created successfully"));
    }

	
	@GetMapping("/current-user")
	public ResponseEntity<?> getCurrentUser(){
		Account currentUser = (Account) request.getAttribute("currentUser");
		if (currentUser != null) {
			return ResponseEntity.ok(currentUser);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not  log in");
	}


	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
	        String email = request.getEmail();

	        // Kiểm tra xem email có tồn tại không
	        if (!accountService.existsByEmail(email)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse("error", "Email not found"));
	        }

	        // Gửi email thông báo yêu cầu thay đổi mật khẩu
	        emailService.sendPasswordResetNotification(email);

	        return ResponseEntity.ok(new ApiResponse("success", "Password reset email sent"));
	    }

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
	        String email = request.getEmail();
	        String newPassword = request.getNewPassword();

	        // Kiểm tra email tồn tại và thay đổi mật khẩu
	        if (!accountService.existsByEmail(email)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse("error", "Email not found"));
	        }

	        accountService.changePassword(email, newPassword); // Thay đổi mật khẩu

	        return ResponseEntity.ok(new ApiResponse("success", "Password successfully reset"));
	    }

	@PutMapping("/account/{accountId}")
	public ResponseEntity<Account> updateAccount(
	    @PathVariable int accountId,
	    @RequestParam(required = false) MultipartFile image,  // For file upload
	    @RequestParam String name,
	    @RequestParam String phone,
	    @RequestParam boolean gender,
	    @RequestParam String email) {

	    // Prepare the updated account object
	    Account updatedAccount = new Account();
	    updatedAccount.setName(name);
	    updatedAccount.setPhone(phone);
	    updatedAccount.setGender(gender);
	    updatedAccount.setEmail(email);

	    // Handle image file if it's uploaded
	    if (image != null && !image.isEmpty()) {
	        try {
	            // Save the image file to a directory (e.g., "uploads")
	            String fileName = saveImage(image);
	            updatedAccount.setImage(fileName); // Save the filename or relative path to the database
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body(null); // Return 500 error if there's an issue with file saving
	        }
	    }

	    try {
	        // Update the account through the service
	        Account account = accountService.updateAccount(accountId, updatedAccount);
	        return ResponseEntity.ok(account); // Return the updated account
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(400).body(null); // Return 400 error if the update fails
	    }
	}

	private String saveImage(MultipartFile image) throws IOException {
	    // Define the directory where images will be stored (e.g., "uploads")
	    String uploadDir = "C:/uploads";  // You can also use relative paths or configurable directory
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        dir.mkdirs(); // Create the directory if it doesn't exist
	    }

	    // Create a unique filename using the current timestamp
	    String fileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
	    File file = new File(uploadDir, fileName);

	    // Save the file
	    image.transferTo(file);

	    // Return the relative path or filename (not the full absolute path)
	    return fileName; // Or you can return a relative path to store in DB
	} 
	
//	@PostMapping("/change-password")
//    public ResponseEntity<?> changePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//
//        // Kiểm tra mật khẩu hiện tại
//        if (!passwordService.checkCurrentPassword(currentPassword)) {
//            return ResponseEntity.badRequest().body("Mật khẩu hiện tại không đúng.");
//        }
//
//        // Cập nhật mật khẩu
//        passwordService.updatePassword(newPassword);
//        return ResponseEntity.ok("Cập nhật mật khẩu thành công.");
//    }
//	
	

}
