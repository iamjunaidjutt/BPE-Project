package com.bpeproject.miniso.Repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bpeproject.miniso.Entity.DailySale;

@Repository
public interface DailySaleRepository extends JpaRepository<DailySale, Long>{
   // Daily Sales
//    List<DailySale> findAll();
//  @Query("Select SUM(totalSales) as revenue, salesDay, salesMonth from DailySale where date >= :startDate AND date <= :endDate GROUP BY salesDay, salesMonth, date;")

//    List<Object[]> veryBad(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


@Query("SELECT d.date AS date, SUM(d.totalSales) AS totalSales FROM DailySale d WHERE d.date BETWEEN :startDate AND :endDate GROUP BY d.date")
List<Object[]> findDailySales(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

// Monthly Sales
@Query("SELECT FUNCTION('YEAR', d.date) AS year, FUNCTION('MONTH', d.date) AS month, SUM(d.totalSales) AS totalSales FROM DailySale d WHERE d.date BETWEEN :startDate AND :endDate GROUP BY FUNCTION('YEAR', d.date), FUNCTION('MONTH', d.date)")
List<Object[]> findMonthlySales(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

// Yearly Sales
@Query("SELECT FUNCTION('YEAR', d.date) AS year, SUM(d.totalSales) AS totalSales FROM DailySale d WHERE d.date BETWEEN :startDate AND :endDate GROUP BY FUNCTION('YEAR', d.date)")
List<Object[]> findYearlySales(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
