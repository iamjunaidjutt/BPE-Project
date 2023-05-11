package com.bpeproject.miniso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bpeproject.miniso.Entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT TOP 1 * from Invoice where id = ?1", nativeQuery = true)
    public Invoice getByIdR(Long id);
}
