package com.fpoly.java6.service;

public interface PaymentService {
	boolean processPayment(int paymentMethod, int totalPrice);
}
