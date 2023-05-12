package com.bpeproject.miniso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bpeproject.miniso.Entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT TOP 1 * from Invoice where id = ?1", nativeQuery = true)
    public Invoice getByIdR(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Invoice SET discountCode = ?2, discount = ?3, totalWithDiscount = ?4, totalWithGST = ?5 where id = ?1", nativeQuery = true)
    public void updateR(Long id, String code, double discount, double totalWithDiscount, double totalWithGST);
}
