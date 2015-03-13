package com.bee32.zebra.io.stock;

import javax.persistence.Table;

import com.bee32.zebra.io.art.Artifact;
import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.IdType;
import com.tinylily.model.base.TableDefaults;

/**
 * 库存操作项目
 */
@IdType(Long.class)
@Table(name = "stentry")
@TableDefaults(accessMode = CoObject.M_SHARED)
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