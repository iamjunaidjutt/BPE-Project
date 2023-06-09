package com.bpeproject.miniso.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

        this.currentInvoice = null;
        this.currentCustomer = null;

        return "getCustomerInfo.html";
    }

    @GetMapping("/home")
    public String home(Model model) {

        return "index.html";
    }

    @GetMapping("/discountprogram")
    public String displayDiscountProgram(@RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber, @RequestParam("invoice") Long invoice, Model model) {

        System.out.println("phoneNumber : " + phoneNumber);
        System.out.println("Name : " + name);
        System.out.println("invoice : " + invoice);

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

    @GetMapping("/applydiscount{id}")
    public String applyDiscount(@PathVariable("id") Long id, Model model) {

        Discount myDiscount = discountService.getById(id);

        currentInvoice.reCalculate(myDiscount);

        myDiscount.setQuantity(myDiscount.getQuantity() - 1);

        discountService.update(myDiscount);

        invoiceService.update(currentInvoice);

        return "discount_success.html";
    }

    @GetMapping("/rejectDiscount")
    public String stopDiscountProcess(Model model) {

        return "discount_reject.html";
    }

    @GetMapping("/generateInvoice")
    public String invoiceGenerate(Model model) {

        model.addAttribute("customer", currentCustomer);
        model.addAttribute("invoice", currentInvoice);

        return "invoice_display.html";
    }

    @GetMapping("/addmore")
    public String addMoreDiscount(Model model) {

        Discount newDiscountP = new Discount();

        model.addAttribute("newDiscountP", newDiscountP);

        return "addDiscount.html";
    }

    @PostMapping("/addmore")
    public String addMoreDiscountPost(@ModelAttribute("newDiscountP") Discount newDiscountP, Model model) {

        discountService.save(newDiscountP);

        return "redirect:/home";
    }

    // @PostMapping("/addmore")
    // public String addMoreDiscountPost(Model model, @RequestParam("amount") double
    // amount,
    // @RequestParam("discountCode") String discountCode, @RequestParam("quantity")
    // int quantity,
    // @RequestParam("validity") Date validity) {

    // Discount newDiscountP = new Discount(discountCode, amount, quantity,
    // validity);

    // discountService.save(newDiscountP);

    // return "redirect:/home";
    // }
}
