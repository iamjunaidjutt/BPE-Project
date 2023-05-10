package com.bpeproject.miniso.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total", nullable = false)
    private float total;

    @Column(name = "discount")
    private float discount;

    @Column(name = "discountCode", nullable = false)
    private String discountCode;

    @Column(name = "totalWithDiscount", nullable = false)
    private float totalWithDiscount;

    @Column(name = "totalWithGST", nullable = false)
    private float totalWithGST;
}
