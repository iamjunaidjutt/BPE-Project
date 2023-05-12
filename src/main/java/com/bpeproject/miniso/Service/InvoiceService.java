package com.bpeproject.miniso.Service;

import org.springframework.stereotype.Service;

import com.bpeproject.miniso.Entity.Invoice;
import com.bpeproject.miniso.Repository.InvoiceRepository;

@Service
public class InvoiceService {

    InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public Invoice getById(Long id) {
        return invoiceRepository.getByIdR(id);
    }

    public void update(Invoice invoice) {
        invoiceRepository.updateR(invoice.getId(), invoice.getDiscountCode(), invoice.getDiscount(),
                invoice.getTotalWithDiscount(), invoice.getTotalWithGST());
    }
}
