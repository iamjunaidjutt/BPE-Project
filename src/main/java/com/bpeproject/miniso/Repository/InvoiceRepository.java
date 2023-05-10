package com.bpeproject.miniso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bpeproject.miniso.Entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
