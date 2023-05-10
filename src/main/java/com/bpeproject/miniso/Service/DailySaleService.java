package com.bpeproject.miniso.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpeproject.miniso.Entity.DailySale;
import com.bpeproject.miniso.Repository.DailySaleRepository;

@Service
public class DailySaleService {

    @Autowired
    DailySaleRepository dailySaleRepository;
    
    
    public void addDailySale(DailySale sale){
        dailySaleRepository.save(sale);
    }

}
