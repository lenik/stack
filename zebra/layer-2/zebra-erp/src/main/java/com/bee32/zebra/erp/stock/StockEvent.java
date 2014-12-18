package com.bee32.zebra.erp.stock;

import java.util.ArrayList;
import java.util.List;

import com.bee32.zebra.oa.contact.Party;
import com.tinylily.model.base.CoMomentInterval;

public class StockEvent
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private Party party;
    private List<StockEntry> entries = new ArrayList<>();

    private double quantity;
    private double total;

    public synchronized void update() {
        quantity = 0;
        total = 0;
        for (StockEntry entry : entries) {
            quantity += entry.getQuantity();
            total += entry.getTotal();
        }
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
