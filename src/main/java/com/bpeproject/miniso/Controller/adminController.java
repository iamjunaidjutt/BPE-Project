// package com.bpeproject.miniso.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// @Controller
// public class AdminController {

@Controller
public class AdminController {
    
    @Autowired
    DailySaleService dailySaleService;
    @GetMapping("/manager/daily-entry")
    public String dailyEntryGet(Model model) {
        model.addAttribute("dailySale", new DailySale()) ;     
        return "dailyEntry";
    }
    
    @PostMapping("/manager/daily-entry")
    public String dailyEntryPost (@ModelAttribute("dailySale") DailySale dailySale) {
        double saleswogst = (int)dailySale.getTotalSales()/1.17;
        dailySale.setTotalSalesWoGST(saleswogst);

        double avgTval =(int)dailySale.getTotalSales()/dailySale.getNumOfCustomers();
        dailySale.setAvgTransactionValue(avgTval);

        dailySaleService.addDailySale(dailySale);
        return "dailyEntry";
    }
}

// }