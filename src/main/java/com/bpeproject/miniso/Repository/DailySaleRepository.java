package com.bpeproject.miniso.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bpeproject.miniso.Entity.DailySale;


public interface DailySaleRepository extends JpaRepository<DailySale, Long>{
    
}
