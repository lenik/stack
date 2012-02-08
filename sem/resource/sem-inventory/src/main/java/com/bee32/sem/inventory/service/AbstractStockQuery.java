package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.world.thing.AbstractItem;
import com.bee32.sem.world.thing.AbstractItemList;

public abstract class AbstractStockQuery
        extends DataService
        implements IStockQuery {

    @Override
    public StockItemList getCurrentStockState() {
        return getHistoryStockState(new Date());
    }

    public abstract StockOrder getSummary(ICriteriaElement selection, StockQueryOptions options);

    @Override
    public StockOrder getActualSummary(List<Long> materialIds, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfCommons(materialIds, options);
        StockOrder summary = getSummary(selection, options);
        summary.setLabel("【汇总】实有库存余量清单");
        return summary;
    }

    @Override
    public StockOrder getVirtualSummary(List<Long> materialIds, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfVirtuals(materialIds, options);
        StockOrder summary = getSummary(selection, options);
        summary.setLabel("【汇总】可用库存余量清单");
        return summary;
    }

    @Override
    public StockOrder getPlanSummary(List<Long> materialIds, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfVirtualOnly(materialIds, options);
        StockOrder summary = getSummary(selection, options);
        summary.setLabel("【汇总】计划/锁定库存数量清单");
        return summary;
    }

    @Override
    public BigDecimal getActualQuantity(long materialId, StockQueryOptions options) {
        if (options == null)
            throw new NullPointerException("options");

        StockOrder list = getActualSummary(Arrays.asList(materialId), options);
        BigDecimal quantity = sumOfQuantity(list);
        return quantity;
    }

    @Override
    public BigDecimal getVirtualQuantity(long materialId, StockQueryOptions options) {
        if (options == null)
            throw new NullPointerException("options");

        StockOrder list = getVirtualSummary(Arrays.asList(materialId), options);
        BigDecimal quantity = sumOfQuantity(list);
        return quantity;
    }

    @Override
    public BigDecimal getPlanQuantity(long materialId, StockQueryOptions options) {
        if (options == null)
            throw new NullPointerException("options");

        StockOrder list = getPlanSummary(Arrays.asList(materialId), options);
        BigDecimal quantity = sumOfQuantity(list);
        return quantity;
    }

    static BigDecimal sumOfQuantity(AbstractItemList<?> list) {
        BigDecimal sum = new BigDecimal(0);
        for (AbstractItem item : list) {
            BigDecimal quantity = item.getQuantity();
            sum = sum.add(quantity);
        }
        return sum;
    }

}
