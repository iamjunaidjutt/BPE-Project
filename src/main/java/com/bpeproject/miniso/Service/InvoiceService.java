package com.bpeproject.miniso.Service;

import com.bpeproject.miniso.Entity.Invoice;
import com.bpeproject.miniso.Repository.InvoiceRepository;

public class InvoiceService {

    InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}
