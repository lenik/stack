package com.bee32.sems.bom.service;

import java.math.BigDecimal;

import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.sem.inventory.entity.Material;

public class LatestPrice
        extends MaterialPriceStrategy {

    private static final long serialVersionUID = 1L;

    public LatestPrice(String value, String name) {
        super(value, name);
    }

    @Override
    public BigDecimal getPrice(IEntityMarshalContext ctx, Material material) {
//    case NEWESTPRICE:
//        rawMaterialPrice = sortedPrices.get(sortedPrices.lastKey());
//        break;
        return null;
    }

}
