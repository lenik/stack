package com.bee32.sem.make.service;

import java.math.BigDecimal;
import java.util.List;

import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialPrice;
import com.bee32.sem.world.monetary.FxrQueryException;

public class AveragePrice
        extends PriceStrategy {

    private static final long serialVersionUID = 1L;

    public AveragePrice(char value, String name) {
        super(value, name);
    }

    @Override
    public BigDecimal getPrice(Material material)
            throws FxrQueryException {
        List<MaterialPrice> prices = material.getPrices();
        if (prices.isEmpty())
            return null;

        BigDecimal total = new BigDecimal(0);
        for (MaterialPrice price : prices) {
            BigDecimal each = price.getNativePrice();
            total = total.add(each);
        }

        BigDecimal average = total.divide(new BigDecimal(prices.size()));
        return average;
    }

}
