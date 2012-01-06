package com.bee32.sems.bom.service;

import java.math.BigDecimal;
import java.util.List;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.monetary.FxrQueryException;

public class AveragePrice
        extends PriceStrategy {

    private static final long serialVersionUID = 1L;

    public AveragePrice(char value, String name) {
        super(value, name);
    }

    @Override
    public BigDecimal getPrice(Material material)
            throws FxrQueryException, MaterialPriceNotFoundException {
        BigDecimal total = new BigDecimal(0);
        int count = 0;

        List<MaterialPrice> prices = material.getPrices();
        for (MaterialPrice price : prices) {
            BigDecimal each = price.getNativePrice();
            total = total.add(each);
            count++;
        }

        if (count == 0)
            throw new MaterialPriceNotFoundException(material);

        BigDecimal average = total.divide(new BigDecimal(count));
        return average;
    }

}
