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
    private double total;

    @Column(name = "discount")
    private double discount;

    @Column(name = "discountCode", nullable = false)
    private String discountCode;

    @Column(name = "totalWithDiscount", nullable = false)
    private double totalWithDiscount;

    @Column(name = "totalWithGST", nullable = false)
    private double totalWithGST;

    public Invoice() {

    }

    public Invoice(double total, double discount, String discountCode, double totalWithDiscount, double totalWithGST) {
        this.total = total;
        this.discount = discount;
        this.discountCode = discountCode;
        this.totalWithDiscount = totalWithDiscount;
        this.totalWithGST = totalWithGST;
    }

    public Invoice(Long id, double total, double discount, String discountCode, double totalWithDiscount,
            double totalWithGST) {
        this.id = id;
        this.total = total;
        this.discount = discount;
        this.discountCode = discountCode;
        this.totalWithDiscount = totalWithDiscount;
        this.totalWithGST = totalWithGST;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    public double getTotalWithGST() {
        return totalWithGST;
    }

    public void setTotalWithGST(double totalWithGST) {
        this.totalWithGST = totalWithGST;
    }

    public void reCalculate(Discount d) {
        this.discountCode = d.getCode();
        this.discount = d.getAmount();
        this.totalWithDiscount = total - discount;
        this.totalWithGST = totalWithDiscount + (totalWithDiscount * 0.17);
    }
}
