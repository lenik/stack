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
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.world.monetary.MCValue;

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
        // TODO getLatestPack.. then, non-virtual
        // TODO or, >date, non-packing, non-virtual

        ProjectionList projection = new ProjectionList(//
                new GroupPropertyProjection("material"), //
                new SumProjection("quantity"), //
                options.getCBatchProjection(), //
                options.getPriceProjection(), //
                options.getExpirationProjection(), //
                options.getLocationProjection(), //
                options.getWarehouseProjection(), //
                options.getParentWarehouseProjection());

        List<Object[]> list = asFor(StockOrderItem.class).listMisc(projection, selection);

        StockOrderSubject subject = StockOrderSubject.PACK_M;
        if (options.getCBatch() != null) {
            subject = StockOrderSubject.PACK_MB;
            if (options.getLocation() != null)
                subject = StockOrderSubject.PACK_MBL;
        }

        StockOrder all = new StockOrder(null, subject, options.getWarehouse());

        // XXX How about: batch == null, location != null ?

        int index = 0;
        for (Object[] line : list) {
            int _column = 0;
            Material _material = (Material) line[_column++];
            BigDecimal _quantity = (BigDecimal) line[_column++];
            String _cBatch = null;
            Date _expire = null;
            MCValue _price = null;
            StockLocation _location = null;
            StockWarehouse _warehouse = null;
            StockWarehouse _parentWarehouse = null;

            if (options.isCBatchVisible()) {
                _cBatch = (String) line[_column++];
                _price = (MCValue) line[_column++];
                _expire = (Date) line[_column++];
            }
            if (options.isLocationVisible()) {
                _location = (StockLocation) line[_column++];
            }
            if (options.isWarehouseVisible()) {
                _warehouse = (StockWarehouse) line[_column++];
                _parentWarehouse = (StockWarehouse) line[_column++];

                if (_warehouse == null)
                    _warehouse = _parentWarehouse;

                if (_location == null) {
                    _location = new StockLocation();
                    _location.setWarehouse(_warehouse);
                }
            }

            StockOrderItem item = new StockOrderItem(all);
            item.setIndex(index++);
            item.setMaterial(_material);
            item.setQuantity(_quantity);
            item.setCBatch(_cBatch);
            item.setPrice(_price);
            item.setExpirationDate(_expire);
            item.setLocation(_location);

            all.addItem(item);
        }
        return all;
    }

}
