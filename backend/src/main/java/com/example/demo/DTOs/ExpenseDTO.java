package com.example.demo.DTOs;

import java.util.Date;

public class ExpenseDTO {

    private Long id;
    private String type;
    private double amount;
    private Date date;

    // Constructors
    public ExpenseDTO() {
    }

    public ExpenseDTO(Long id, String type, double amount, Date date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
