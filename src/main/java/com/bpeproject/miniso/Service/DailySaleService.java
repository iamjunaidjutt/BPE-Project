package com.bpeproject.miniso.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpeproject.miniso.Entity.DailySale;
import com.bpeproject.miniso.Repository.DailySaleRepository;

@Service
public class DailySaleService {

    @Autowired
    DailySaleRepository dailySaleRepository;

    public void addDailySale(DailySale sale) {

        Optional<DailySale> entry = dailySaleRepository.findByDate(sale.getDate());

        if (!entry.isPresent())
            dailySaleRepository.save(sale);
        else {
            sale.setId(entry.get().getId());
            dailySaleRepository.save(sale);
        }
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
                System.out.println("Date: " + ob[0] + ", Revenue: " + ((Double) ob[1]).intValue());
            }
            return stats;
        } else if (interval.equals("weekly")) {
            stats = dailySaleRepository.findWeeklySales(startDate, endDate);
            for (Object[] ob : stats) {
                System.out.println("Week Start: " + ob[0] + ", Revenue: " + ((Double) ob[1]).intValue());
            }
            return stats;
        } else if (interval.equals("monthly")) {
            stats = dailySaleRepository.findMonthlySales(startDate, endDate);
            for (Object[] ob : stats) {
                System.out
                        .println("Month: " + ob[0] + ", Year: " + ob[1] + ", Revenue: " + ((Double) ob[2]).intValue());
            }
            return stats;
        }

        else if (interval.equals("yearly")) {
            stats = dailySaleRepository.findYearlySales(startDate, endDate);
            for (Object[] ob : stats) {
                System.out.println("Year: " + ob[0] + ", Revenue: " + ((Double) ob[1]).intValue());
            }
            return stats;
        } else {
            throw new IllegalArgumentException("Invalid interval: " + interval);
        }

    }

    public List<Integer> pcChanges(List<Object[]> sales) {
        List<Integer> pcChanges = new ArrayList<Integer>();
        Double previousSales = null;

        for (Object[] ob : sales) {
            Double currentSales;
            try {
                currentSales = ((Double) ob[2]);
                // continue processing
            } catch (ArrayIndexOutOfBoundsException e) {
                currentSales = ((Double) ob[1]);
            }

            Double percentageChange = 0.0;
            if (previousSales != null)
                percentageChange = ((currentSales - previousSales) / previousSales) * 100;

            previousSales = currentSales;
            pcChanges.add(percentageChange.intValue());
        }

        return pcChanges;
    }

    public List<Integer> pcBaseChanges(List<Object[]> sales) {
        List<Integer> pcChanges = new ArrayList<Integer>();
        Double base;

        try {
            base = ((Double) sales.get(0)[2]);
            // continue processing
        } catch (ArrayIndexOutOfBoundsException e) {
            base = ((Double) sales.get(0)[1]);
        }

        for (Object[] ob : sales) {
            Double currentSales;

            try {
                currentSales = ((Double) ob[2]);
                // continue processing
            } catch (ArrayIndexOutOfBoundsException e) {
                currentSales = ((Double) ob[1]);
            }

            Double percentageChange = ((currentSales - base) / base) * 100;

            pcChanges.add(percentageChange.intValue());
        }

        return pcChanges;
    }

    public void saleGenerator(int numSales) {
        Random rand = new Random();

        for (int i = 0; i < numSales; i++) {
            // Generate a random date in the past 4 years
            long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
            long maxDay = LocalDate.now().toEpochDay();
            long randomDay = minDay + rand.nextInt((int) (maxDay - minDay));
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

            // Generate a random total sales value between 100k and 400k
            double randomTotalSales = 100000 + (400000 - 100000) * rand.nextDouble();

            // Generate random total customers based on sales
            int randomTotalCustomers;
            if (randomTotalSales <= 250000) { // Sales are low
                randomTotalCustomers = 30 + rand.nextInt(51); // 30 to 80
            } else { // Sales are high
                randomTotalCustomers = 60 + rand.nextInt(91); // 60 to 150
            }

            // Generate random total products sold (1-4 times the number of customers)
            int randomTotalProductsSold = randomTotalCustomers * (1 + rand.nextInt(4));

            // Create and save the new DailySale
            DailySale dailySale = new DailySale();
            dailySale.setDate(randomDate);
            dailySale.setTotalSales(randomTotalSales);
            dailySale.setTotalProducts(randomTotalProductsSold);
            dailySale.setNumOfCustomers(randomTotalCustomers);

            double saleswogst = (int) dailySale.getTotalSales() / 1.17;
            dailySale.setTotalSalesWoGST(saleswogst);

            double avgTval = (int) dailySale.getTotalSales() / dailySale.getNumOfCustomers();
            dailySale.setAvgTransactionValue(avgTval);

            dailySale.setSalesDay(dailySale.getDate().getDayOfMonth());
            dailySale.setSalesMonth(dailySale.getDate().getMonthValue());
            dailySale.setSalesYear(dailySale.getDate().getYear());

            dailySaleRepository.save(dailySale);
        }
    }

}
