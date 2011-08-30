package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.free.NotImplementedException;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
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

        List<?> list = asFor(StockOrderItem.class).listMisc(//
                new GroupPropertyProjection("material"), //
                new SumProjection("quantity"), //
                GroupPropertyProjection.optional("cbatch", cbatch), //
                GroupPropertyProjection.optional("location", location), //
                StockCriteria.sumOfCommons(date, materials, cbatch, location));

        return null;
    }

    @Override
    public Map<Material, BigDecimal> getVirtualQuantity(Date date, List<Material> materials, String cbatch,
            StockLocation location) {
        List<?> list = asFor(StockOrderItem.class).listMisc(//
                new GroupPropertyProjection("material"), //
                new SumProjection("quantity"), //
                GroupPropertyProjection.optional("cbatch", cbatch), //
                GroupPropertyProjection.optional("location", location), //
                StockCriteria.sumOfVirtuals(date, materials, cbatch, location));

        return null;
    }

}
