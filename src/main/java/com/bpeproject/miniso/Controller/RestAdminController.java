package com.bpeproject.miniso.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.bpeproject.miniso.Entity.DailySale;
import com.bpeproject.miniso.Service.DailySaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAdminController {

    @Autowired
    DailySaleService dailySaleService;

    @GetMapping("rest")
    public String homeGetRest(){
        return "home";
    }

    @GetMapping("/rest-daily-entry")
    public DailySale dailyEntryGetRest() {
        return new DailySale();
    }

    @PostMapping("/rest-daily-entry")
    public ResponseEntity<DailySale> dailyEntryPostRest(@RequestBody DailySale dailySale) {
        if (dailySale.getNumOfCustomers() == 0) {
            throw new IllegalArgumentException("Number of customers cannot be zero");
        }

        double saleswogst = (int) dailySale.getTotalSales() / 1.17;
        dailySale.setTotalSalesWoGST(saleswogst);

        double avgTval = (int) dailySale.getTotalSales() / dailySale.getNumOfCustomers();
        dailySale.setAvgTransactionValue(avgTval);

        dailySale.setSalesDay(dailySale.getDate().getDayOfMonth());
        dailySale.setSalesMonth(dailySale.getDate().getMonthValue());
        dailySale.setSalesYear(dailySale.getDate().getYear());
        dailySaleService.addDailySale(dailySale);
        return ResponseEntity.ok(dailySale);
    }


    @GetMapping("/rest-view-sales")
    public String viewSalesGetRest() {
        return "viewSales";
    }

    @PostMapping("/rest-view-sales")
    public ResponseEntity<Map<String, Object>> processDatesRest(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("interval") String interval) {

        Map<String, Object> result = new HashMap<>();

        interval = interval.toLowerCase();
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        if (startDate.isAfter(endDate)) {
            result.put("errorMessage", "Start date must be before end date.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        List<Object[]> intervalStats = dailySaleService.getSalesBetweenDates(interval, startDate, endDate);
        List<Integer> pcChanges = dailySaleService.pcChanges(intervalStats);
        List<Integer> pcBaseChanges = dailySaleService.pcBaseChanges(intervalStats);

        result.put("datesSelected", true);
        result.put("intervalStats", intervalStats);
        result.put("pcStats", pcChanges);
        result.put("pcBaseStats", pcBaseChanges);
        result.put("interval", interval);

        result.put("startDate", startDate);
        result.put("endDate", endDate);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
