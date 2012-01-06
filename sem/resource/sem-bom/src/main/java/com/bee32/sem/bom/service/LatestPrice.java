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

    @Override
    public BigDecimal getPrice(Material material)
            throws FxrQueryException, MaterialPriceNotFoundException {
        MaterialPrice latestPrice = material.getLatestPrice();

        if (latestPrice == null)
            throw new MaterialPriceNotFoundException(material);

        BigDecimal price = latestPrice.getNativePrice();
        return price;
    }

}
