package com.bpeproject.miniso.Service;

import com.bpeproject.miniso.Entity.Discount;
import com.bpeproject.miniso.Repository.DiscountRepository;

public class DiscountService {

    DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void save(Discount discount) {
        discountRepository.save(discount);
    }
}
