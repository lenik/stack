package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.ProjectionList;
import com.bee32.plover.criteria.hibernate.SumProjection;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.inventory.util.BatchMetadata;
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
    public StockQueryResult getStock(ICriteriaElement selection, StockQueryOptions options) {
        // TODO getLatestPack.. then, non-virtual
        // TODO or, >date, non-packing, non-virtual

        ProjectionList projection = new ProjectionList(//
                new GroupPropertyProjection("material"), //
                new SumProjection("quantity"), //
                options.getPriceProjection(), //
                options.getExpirationProjection(), //
                options.getLocationProjection(), //
                options.getWarehouseProjection(), //
                options.getParentWarehouseProjection());

        options.fillBatchProjections(projection);

        List<Object[]> list = ctx.data.access(StockOrderItem.class).listMisc(projection, selection);

        StockOrderSubject subject = StockOrderSubject.PACK_M;
        if (options.getBatchArray() != null) {
            subject = StockOrderSubject.PACK_MB;
            if (options.getLocation() != null)
                subject = StockOrderSubject.PACK_MBL;
        }

        StockQueryResult all = new StockQueryResult(null, subject, null/* warehouse */);

        // XXX How about: batch == null, location != null ?

        int index = 0;
        for (Object[] line : list) {
            int _column = 0;
            Material _material = (Material) line[_column++];
            BigDecimal _quantity = (BigDecimal) line[_column++];
            BatchArray _batchArray = null;
            Date _expire = null;
            MCValue _price = null;
            StockLocation _location = null;
            StockWarehouse _warehouse = null;
            StockWarehouse _parentWarehouse = null;

            if (options.isBatchArrayVisible()) {
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

            // Extensible batch columns.
            if (options.isBatchArrayVisible()) {
                BatchMetadata metadata = BatchMetadata.getInstance();
                _batchArray = new BatchArray();
                for (int i = 0; i < metadata.getArraySize(); i++)
                    _batchArray.setBatch(i, (String) line[_column++]);
            }

            StockOrderItem item = new StockOrderItem(all);
            item.setIndex(index++);
            EntityAccessor.setId(item, (long) -index);
            EntityAccessor.setVersion(item, -1); // A negative version will be skipped in DTO.
            item.setMaterial(_material);
            item.setQuantity(_quantity);
            if (options.isBatchArrayVisible()) {
                item.setBatchArray(_batchArray);
                item.setPrice(_price);
                item.setExpirationDate(_expire);
            }
            if (options.isWarehouseVisible())
                ; // item.setWarehouse();
            if (options.isLocationVisible()) {
                item.setLocation(_location);
            }

            all.addItem(item);
        }
        return all;
    }

}
