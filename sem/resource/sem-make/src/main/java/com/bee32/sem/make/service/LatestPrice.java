package com.bee32.sem.make.service;

import java.math.BigDecimal;

import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialPrice;
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
