package com.bee32.sem.bom.service;

import java.math.BigDecimal;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.monetary.FxrQueryException;

public class LatestPrice
        extends PriceStrategy {

    private static final long serialVersionUID = 1L;

    public LatestPrice(char value, String name) {
        super(value, name);
    }

    /**
     *
     */
    @Override
    public BigDecimal getPrice(Material material)
            throws FxrQueryException {
        MaterialPrice latestPrice = material.getLatestPrice();
        if (latestPrice != null)
            return latestPrice.getNativePrice();
        else
            return null;
    }

}
