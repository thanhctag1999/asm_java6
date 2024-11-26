package com.fpoly.java6.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
    public boolean processPayment(int paymentMethod, int totalPrice) {
        // Xử lý phương thức thanh toán dựa trên paymentMethod
        // Giả sử có 2 phương thức thanh toán:
        // 1: Thanh toán khi nhận hàng
        // 2: Thanh toán qua thẻ tín dụng

        switch (paymentMethod) {
            case 1:
                // Thanh toán khi nhận hàng (COD - Cash On Delivery)
                return processCashOnDelivery(totalPrice);
            case 2:
                // Thanh toán qua thẻ tín dụng
                return processCreditCardPayment(totalPrice);
            default:
                throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ");
        }
    }
	
	private boolean processCashOnDelivery(int totalPrice) {
        // Xử lý thanh toán khi nhận hàng (COD)
        // Giả sử thanh toán khi nhận hàng luôn thành công
        System.out.println("Đang xử lý thanh toán khi nhận hàng, tổng giá trị: " + totalPrice);
        return true;
    }

    private boolean processCreditCardPayment(int totalPrice) {
        // Xử lý thanh toán qua thẻ tín dụng
        // Đây là ví dụ mô phỏng, bạn có thể tích hợp với API thanh toán thực tế như Stripe, PayPal, v.v.
        System.out.println("Đang xử lý thanh toán qua thẻ tín dụng, tổng giá trị: " + totalPrice);

        // Giả sử thanh toán qua thẻ tín dụng luôn thành công
        return true;
    }
}
