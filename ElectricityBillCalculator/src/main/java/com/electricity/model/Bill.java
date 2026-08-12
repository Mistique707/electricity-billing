package com.electricity.model;

import java.sql.Timestamp;

/**
 * Represents a single electricity bill and the slab-wise breakdown
 * used to arrive at the total amount.
 */
public class Bill {

    private int id;
    private int userId;
    private int units;

    private double slab1Amount;
    private double slab2Amount;
    private double slab3Amount;
    private double slab4Amount;

    private double totalAmount;

    private Timestamp createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getSlab1Amount() {
        return slab1Amount;
    }

    public void setSlab1Amount(double slab1Amount) {
        this.slab1Amount = slab1Amount;
    }

    public double getSlab2Amount() {
        return slab2Amount;
    }

    public void setSlab2Amount(double slab2Amount) {
        this.slab2Amount = slab2Amount;
    }

    public double getSlab3Amount() {
        return slab3Amount;
    }

    public void setSlab3Amount(double slab3Amount) {
        this.slab3Amount = slab3Amount;
    }

    public double getSlab4Amount() {
        return slab4Amount;
    }

    public void setSlab4Amount(double slab4Amount) {
        this.slab4Amount = slab4Amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
