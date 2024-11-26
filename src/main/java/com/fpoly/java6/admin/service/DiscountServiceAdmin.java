package com.fpoly.java6.admin.service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.admin.repository.DiscountRepository;
import com.fpoly.java6.entities.Discount;

@Service
public class DiscountServiceAdmin {

    @Autowired
    private DiscountRepository discountRepository;

    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    public Discount findById(int discountId) {
        return discountRepository.findById(discountId).orElse(null);
    }

    public boolean deleteDiscount(int discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
            return true;
        }
        return false;
    }
    
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }



}
