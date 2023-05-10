package com.bpeproject.miniso.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bpeproject.miniso.Entity.Discount;
import com.bpeproject.miniso.Repository.DiscountRepository;

@Service
public class DiscountService {

    DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    public List<Discount> getAll() {
        return discountRepository.getAll();
    }

    public List<Discount> getByCode(String code) {
        return discountRepository.getByCode(code);
    }
}
