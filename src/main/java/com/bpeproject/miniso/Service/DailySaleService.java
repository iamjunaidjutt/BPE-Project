package com.bpeproject.miniso.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpeproject.miniso.Entity.DailySale;
import com.bpeproject.miniso.Repository.DailySaleRepository;

@Service
public class DailySaleService {

    @Autowired
    DailySaleRepository dailySaleRepository;

    public void addDailySale(DailySale sale) {
        dailySaleRepository.save(sale);
    }

    // public List<Object[]> findDailyStats(LocalDate startDate, LocalDate endDate){
    // return dailySaleRepository.findDailySales(startDate, endDate);
    // }

    public List<Object[]> findMonthlyStats(LocalDate startDate, LocalDate endDate) {
        return dailySaleRepository.findMonthlySales(startDate, endDate);
    }

    public List<Object[]> findYearlyStats(LocalDate startDate, LocalDate endDate) {
        return dailySaleRepository.findYearlySales(startDate, endDate);
    }

    public List<Object[]> getSalesBetweenDates(String interval, LocalDate startDate, LocalDate endDate) {
        List<Object[]> stats;
        System.out.println(interval + ": " + startDate + " - " + endDate);
        if (interval.equals("daily")) {
            stats = dailySaleRepository.findDailySales(startDate, endDate);
            for (Object[] ob : stats) {
                System.out.println("Date: " + ob[0] + ", Revenue: " + ob[1]);
            }
            return stats;
        } else if (interval.equals("monthly")) {
            stats = dailySaleRepository.findMonthlySales(startDate, endDate);
            for (Object[] ob : stats) {
                System.out.println("Month: " + ob[0] + ", Year: " + ob[1] + ", Revenue: " + ob[2]);
            }
            return stats;
        }

        else if (interval.equals("yearly")) {
            stats = dailySaleRepository.findYearlySales(startDate, endDate);
            for (Object[] ob : stats) {
                System.out.println("Year: " + ob[0] + ", Revenue: " + ob[1]);
            }
            return stats;
        } else {
            throw new IllegalArgumentException("Invalid interval: " + interval);
        }

        // switch (interval.toLowerCase()) {
        // case "daily":
        // stats = dailySaleRepository.findDailySales(startDate, endDate);
        // for (Object[] ob : stats) {
        // System.out.println("Date: " + ob[0] + ", Revenue: " + ob[1]);
        // }
        // return stats;

        // case "monthly":
        // stats = dailySaleRepository.findMonthlySales(startDate, endDate);
        // for (Object[] ob : stats) {
        // System.out.println("Month: " + ob[0] + ", Year: " + ob[1] + ", Revenue: " +
        // ob[2]);
        // }
        // return stats;
        // case "yearly":
        // stats = dailySaleRepository.findYearlySales(startDate, endDate);
        // for (Object[] ob : stats) {
        // System.out.println("Year: " + ob[0] + ", Revenue: " + ob[1]);
        // }
        // return stats;
        // default:
        // throw new IllegalArgumentException("Invalid interval: " + interval);
        // }
        //
    }

}
