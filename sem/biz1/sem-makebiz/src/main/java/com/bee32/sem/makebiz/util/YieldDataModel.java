package com.bee32.sem.makebiz.util;

public class YieldDataModel {
    int quantity;
    int total;
    double time;

    public YieldDataModel(int quantity, int total, double time) {
        this.quantity = quantity;
        this.total = total;
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

}