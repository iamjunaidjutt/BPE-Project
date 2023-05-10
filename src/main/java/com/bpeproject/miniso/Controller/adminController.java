package com.bpeproject.miniso.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bpeproject.miniso.Entity.DailySale;

@Controller
public class adminController {
    
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

        
        return "dailyEntry";
    }
}
