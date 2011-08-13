package com.bee32.sems.bom.service;

import java.math.BigDecimal;

import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.sem.inventory.entity.Material;

public class AveragePrice
        extends MaterialPriceStrategy {

    private static final long serialVersionUID = 1L;

    public AveragePrice(String value, String name) {
        super(value, name);
    }

    @Override
    public BigDecimal getPrice(IEntityMarshalContext context, Material material) {
//    case AVGPRICE:
//        double sumPrice = 0;
//        for (Double eachPrice : sortedPrices.values()) {
//            sumPrice += eachPrice;
//        }
//        rawMaterialPrice = sumPrice / sortedPrices.size();
//        break;
//    }
        return null;
    }

}
