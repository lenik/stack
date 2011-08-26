package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.free.NotImplementedException;

import com.bee32.plover.criteria.hibernate.ProjectionElement;
import com.bee32.plover.criteria.hibernate.PropertyProjection;
import com.bee32.plover.criteria.hibernate.SumProjection;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.StockCriteria;

public class StockQuery
        extends AbstractStockQuery {

    @Override
    public StockItemList getHistoryStockState(Date date) {
        throw new NotImplementedException();
    }

    @Override
    public StockItemList getStockChange(Date begin, Date end, boolean fullSpan) {
        throw new NotImplementedException();
    }

    @Override
    public Map<Material, BigDecimal> getActualQuantity(Date date, List<Material> materials, String cbatch,
            StockLocation location) {
        // getLatestPack.. then, non-virtual
        // or, >date, non-packing, non-virtual

        ProjectionElement cbatchGrouping = null;
        if (cbatch != null)
            cbatchGrouping = new PropertyProjection("cbatch", true);

        List<BigDecimal> quantities = asFor(StockOrderItem.class).listMisc(//
                new SumProjection("quantity"), //
                new PropertyProjection("material", true), //
                cbatchGrouping, //
                StockCriteria.actualsNoCache(date, materials, cbatch));

        return null;
    }

    @Override
    public Map<Material, BigDecimal> getAvailableQuantity(Date date, List<Material> materials, String cbatch,
            StockLocation location) {
        return null;
    }

}
