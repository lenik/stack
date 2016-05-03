package com.bee32.zebra.io.stock;

import javax.persistence.Table;

import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.CoMomentInterval;

import com.bee32.zebra.io.art.Artifact;

/**
 * 库存操作项目
 */
@IdType(Long.class)
@Table(name = "stentry")
public class StockEntry
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;

    public static final int N_BATCH = 30;
    public static final int N_DIVS = 100;

    StockEvent event;

    Artifact artifact;
    Place place;
    String batch;
    String divs;

    double quantity;
    double price;

    @Override
    public void instantiate() {
        setAccessMode(M_SHARED);
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return quantity * price;
    }

}