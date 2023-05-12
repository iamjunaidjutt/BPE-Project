package com.bpeproject.miniso.Entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

   private  int numOfCustomers;
   private  double totalSales;
   private  int totalProducts;


   @Column(name = "date")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate date;
   private int salesDay;
   private int salesMonth;
   private int salesYear;
    private double totalSalesWoGST;
    private double avgTransactionValue;
    
    DailySale(int customers, int sales, int products){
        numOfCustomers = customers;
        totalSales = sales;
        totalProducts = products;
    }
}
