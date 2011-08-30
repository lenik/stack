package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;

public abstract class AbstractStockQuery
        extends DataService
        implements IStockQuery {

    @Override
    public StockItemList getCurrentStockState() {
        return getHistoryStockState(new Date());
    }

    @Override
    public BigDecimal getActualQuantity(Date date, Material material) {
        if (date == null)
            throw new NullPointerException("date");
        if (material == null)
            throw new NullPointerException("material");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        Map<Material, BigDecimal> map = getActualQuantity(date, _materials, null, null);
        BigDecimal quantity = map.get(material);
        return quantity;
    }

    @Override
    public BigDecimal getAvailableQuantity(Date date, Material material) {
        if (date == null)
            throw new NullPointerException("date");
        if (material == null)
            throw new NullPointerException("material");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        Map<Material, BigDecimal> map = getVirtualQuantity(date, _materials, null, null);
        BigDecimal quantity = map.get(material);
        return quantity;
    }

}
