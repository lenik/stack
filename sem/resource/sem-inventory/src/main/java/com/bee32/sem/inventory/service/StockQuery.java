package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.ProjectionList;
import com.bee32.plover.criteria.hibernate.SumProjection;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;

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
    public StockOrder getSummary(ICriteriaElement selection, StockQueryOptions options) {
        // getLatestPack.. then, non-virtual
        // or, >date, non-packing, non-virtual

        ProjectionList projection = new ProjectionList(//
                new GroupPropertyProjection("material"), //
                new SumProjection("quantity"), //
                options.getCbatchProjection(), //
                options.getExpirationProjection(), //
                options.getLocationProjection(), //
                options.getWarehouseProjection());

        List<Object[]> list = asFor(StockOrderItem.class).listMisc(projection, selection);

        StockOrder all = new StockOrder();

        StockOrderSubject subject = StockOrderSubject.PACK_M;
        if (options.getCbatch() != null) {
            subject = StockOrderSubject.PACK_MB;
            if (options.getLocation() != null)
                subject = StockOrderSubject.PACK_MBL;
        }
        // XXX How about: batch == null, location != null ?
        all.setSubject(subject);
        all.setWarehouse(options.getWarehouse());

        for (Object[] line : list) {
            int _column = 0;
            Material _material = (Material) line[_column++];
            BigDecimal _quantity = (BigDecimal) line[_column++];
            String _cbatch = null;
            Date _expire = null;
            StockLocation _location = null;

            if (!options.isCbatchMerged()) {
                _cbatch = (String) line[_column++];
                _expire = (Date) line[_column++];
            }
            if (!options.isLocationMerged()) {
                _location = (StockLocation) line[_column++];
            }

            StockOrderItem item = new StockOrderItem(all);
            item.setMaterial(_material);
            item.setQuantity(_quantity);
            item.setCBatch(_cbatch);
            item.setExpirationDate(_expire);
            item.setLocation(_location);

            all.addItem(item);
        }

        return all;
    }

}
