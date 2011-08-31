package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.StockCriteria;

public abstract class AbstractStockQuery
        extends DataService
        implements IStockQuery {

    @Override
    public StockItemList getCurrentStockState() {
        return getHistoryStockState(new Date());
    }

    public abstract StockItemList getSummary(ICriteriaElement selection, Date date, List<Material> materials,
            String cbatch, StockLocation location);

    @Override
    public StockItemList getActualSummary(Date date, List<Material> materials, String cbatch, StockLocation location) {
        ICriteriaElement selection = StockCriteria.sumOfCommons(date, materials, cbatch, location);
        return getSummary(selection, date, materials, cbatch, location);
    }

    @Override
    public StockItemList getVirtualSummary(Date date, List<Material> materials, String cbatch, StockLocation location) {
        ICriteriaElement selection = StockCriteria.sumOfVirtuals(date, materials, cbatch, location);
        return getSummary(selection, date, materials, cbatch, location);
    }

    @Override
    public BigDecimal getActualQuantity(Date date, Material material, String cbatch, StockLocation location) {
        if (date == null)
            throw new NullPointerException("date");
        if (material == null)
            throw new NullPointerException("material");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        StockItemList list = getActualSummary(date, _materials, cbatch, location);
        StockOrderItem first = list.getItems().get(0);
        return first.getQuantity();
    }

    @Override
    public BigDecimal getAvailableQuantity(Date date, Material material, String cbatch, StockLocation location) {
        if (date == null)
            throw new NullPointerException("date");
        if (material == null)
            throw new NullPointerException("material");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        StockItemList list = getVirtualSummary(date, _materials, cbatch, location);
        StockOrderItem first = list.getItems().get(0);
        return first.getQuantity();
    }

}
