package com.bpeproject.miniso.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpeproject.miniso.Entity.Customer;
import com.bpeproject.miniso.Entity.Discount;
import com.bpeproject.miniso.Entity.Invoice;
import com.bpeproject.miniso.Service.DiscountService;
import com.bpeproject.miniso.Service.InvoiceService;

@Controller
public class AppController {

    InvoiceService invoiceService;
    DiscountService discountService;
    Customer currentCustomer;
    Invoice currentInvoice;

    public AppController(InvoiceService invoiceService, DiscountService discountService) {
        this.invoiceService = invoiceService;
        this.discountService = discountService;
    }

    @GetMapping("/startdiscount")
    public String getCustomerInfo(Model model) {

        return "getCustomerInfo.html";
    }

    @GetMapping("/discountprogram")
    public String displayDiscountProgram(@RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber, @RequestParam("invoice") Long invoice, Model model) {

        this.currentCustomer = new Customer(name, phoneNumber);

        this.currentInvoice = invoiceService.getById(invoice);

        List<Discount> discount = discountService.getAll();

        model.addAttribute("discount", discount);

        Discount discountSearch = new Discount();

        model.addAttribute("discountSearch", discountSearch);

        return "discountPrograms.html";
    }

    @GetMapping("/searchCode")
    public String applyDiscount(@RequestParam("code") String code, Model model) {

        discountService.getByCode(code);

        List<Discount> discount = discountService.getByCode(code);
        // System.out.println("Size : " + discount.size());
        model.addAttribute("discount", discount);

        return "discount_apply.html";
    }

    @GetMapping("/")
}
