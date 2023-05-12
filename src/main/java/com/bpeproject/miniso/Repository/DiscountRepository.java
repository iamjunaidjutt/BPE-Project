package com.bpeproject.miniso.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bpeproject.miniso.Entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query(value = "SELECT * from Discount", nativeQuery = true)
    public List<Discount> getAll();

    @Query(value = "SELECT d.* from Discount d where d.code LIKE %:code%", nativeQuery = true)
    public List<Discount> getByCode(@Param("code") String code);

    @Query(value = "SELECT d.* from Discount d where d.id = ?1", nativeQuery = true)
    public Discount getByIdR(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Discount d SET quantity = :quantity where id = :id", nativeQuery = true)
    public void updateQuantityById(@Param("id") Long id, @Param("quantity") int quantity);
}
