package com.bpeproject.miniso.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bpeproject.miniso.Entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query(value = "SELECT * from Discount", nativeQuery = true)
    public List<Discount> getAll();

    @Query(value = "SELECT d.* from Discount d where d.code LIKE %:code%", nativeQuery = true)
    public List<Discount> getByCode(@Param("code") String code);
}
