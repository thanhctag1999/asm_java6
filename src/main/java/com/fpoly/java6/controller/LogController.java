package com.fpoly.java6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.jpa.AccountJPA;

@Controller
@RequestMapping("/conan")
public class LogController {

	@Autowired
	AccountJPA accountJPA;

	@GetMapping("/index")
	public String logIndex(Model model) {
		return "user/index";
	}

	@GetMapping("/about")
	public String logAbout(Model model) {
		return "user/about";
	}

	@GetMapping("/cart")
	public String logCart(Model model) {
		return "user/cart";
	}

	@GetMapping("/contact")
	public String logContact(Model model) {
		return "user/contact";
	}

	@GetMapping("/forgotPassword")
	public String logForgotPassword(Model model) {
		return "user/forgotPassword";
	}

	@GetMapping("/login")
	public String logLogin(Model model) {
		return "user/login";
	}

	@GetMapping("/signUp")
	public String logSignUp(Model model) {
		return "user/signUp";
	}

	@GetMapping("/payment")
	public String logPayment(Model model) {
		return "user/payment";
	}

	@GetMapping("/product_detail")
	public String logProductDetail(Model model) {
		return "user/product_detail";
	}


	@GetMapping("/updateProfile")
	public String logUpdateProfile(Model model) {
		return "user/updateProfile";
	}

	
}
