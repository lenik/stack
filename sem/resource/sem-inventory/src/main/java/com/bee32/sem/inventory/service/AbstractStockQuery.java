package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.world.thing.AbstractOrderItem;

public abstract class AbstractStockQuery
        extends DataService
        implements IStockQuery {

    @Override
    public StockItemList getCurrentStockState() {
        return getHistoryStockState(new Date());
    }

    public abstract StockItemList getSummary(ICriteriaElement selection, StockQueryOptions options);

    @Override
    public StockItemList getActualSummary(List<Material> materials, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfCommons(materials, options);
        StockItemList summary = getSummary(selection, options);
        summary.setLabel("【汇总】实有库存余量清单");
        return summary;
    }

    @Override
    public StockItemList getVirtualSummary(List<Material> materials, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfVirtuals(materials, options);
        StockItemList summary = getSummary(selection, options);
        summary.setLabel("【汇总】可用库存余量清单");
        return summary;
    }

    @Override
    public StockItemList getPlanSummary(List<Material> materials, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfVirtualOnly(materials, options);
        StockItemList summary = getSummary(selection, options);
        summary.setLabel("【汇总】计划/锁定库存数量清单");
        return summary;
    }

    @Override
    public BigDecimal getActualQuantity(Material material, StockQueryOptions options) {
        if (material == null)
            throw new NullPointerException("material");
        if (options == null)
            throw new NullPointerException("options");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        StockItemList list = getActualSummary(_materials, options);
        BigDecimal quantity = sumOfQuantity(list);
        return quantity;
    }

    @Override
    public BigDecimal getVirtualQuantity(Material material, StockQueryOptions options) {
        if (material == null)
            throw new NullPointerException("material");
        if (options == null)
            throw new NullPointerException("options");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        StockItemList list = getVirtualSummary(_materials, options);
        BigDecimal quantity = sumOfQuantity(list);
        return quantity;
    }

    @Override
    public BigDecimal getPlanQuantity(Material material, StockQueryOptions options) {
        if (material == null)
            throw new NullPointerException("material");
        if (options == null)
            throw new NullPointerException("options");

        List<Material> _materials = new ArrayList<Material>(1);
        _materials.add(material);

        StockItemList list = getPlanSummary(_materials, options);
        BigDecimal quantity = sumOfQuantity(list);
        return quantity;
    }

    static BigDecimal sumOfQuantity(StockItemList list) {
        BigDecimal sum = new BigDecimal(0);
        for (AbstractOrderItem item : list) {
            BigDecimal quantity = item.getQuantity();
            sum = sum.add(quantity);
        }
        return sum;
    }

}
