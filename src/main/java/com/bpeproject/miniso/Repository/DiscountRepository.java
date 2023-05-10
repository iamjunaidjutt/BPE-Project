package com.bpeproject.miniso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bpeproject.miniso.Entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
