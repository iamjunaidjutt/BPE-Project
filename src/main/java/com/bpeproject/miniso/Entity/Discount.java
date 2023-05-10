package com.bpeproject.miniso.Entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "validity", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validity;

    public Discount() {
    }

    public Discount(String code, float amount, int quantity, Date validity) {
        this.code = code;
        this.amount = amount;
        this.quantity = quantity;
        this.validity = validity;
    }

    public Discount(Long id, String code, float amount, int quantity, Date validity) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.quantity = quantity;
        this.validity = validity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

}
