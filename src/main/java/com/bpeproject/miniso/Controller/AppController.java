package com.bpeproject.miniso.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bpeproject.miniso.Entity.Invoice;
import com.bpeproject.miniso.Service.DiscountService;
import com.bpeproject.miniso.Service.InvoiceService;

@Controller
public class AppController {

    InvoiceService invoiceService;
    DiscountService discountService;

    public AppController(InvoiceService invoiceService, DiscountService discountService) {
        this.invoiceService = invoiceService;
        this.discountService = discountService;
    }

}
