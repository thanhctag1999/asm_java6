package com.fpoly.java6.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
	  private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    // Mã hóa mật khẩu
	    public static String encodePassword(String rawPassword) {
	        return passwordEncoder.encode(rawPassword);
	    }

	    // Kiểm tra mật khẩu có khớp với hash hay không
	    public static boolean matches(String rawPassword, String encodedPassword) {
	        return passwordEncoder.matches(rawPassword, encodedPassword);
	    }
}
