package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.inventory.entity.StockItemList;
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

    public abstract StockQueryResult getStock(ICriteriaElement selection, StockQueryOptions options);

    @Override
    public StockQueryResult getPhysicalStock(List<Long> materialIds, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfCommons(materialIds, options);
        StockQueryResult result = getStock(selection, options);
        result.setLabel("【汇总】实有库存余量清单");
        return result;
    }

    @Override
    public StockQueryResult getAvailableStock(List<Long> materialIds, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfVirtuals(materialIds, options);
        StockQueryResult result = getStock(selection, options);
        result.setLabel("【汇总】可用库存余量清单");
        return result;
    }

    @Override
    public StockQueryResult getPlanSummary(List<Long> materialIds, StockQueryOptions options) {
        ICriteriaElement selection = StockCriteria.sumOfVirtualOnly(materialIds, options);
        StockQueryResult result = getStock(selection, options);
        result.setLabel("【汇总】计划/锁定库存数量清单");
        return result;
    }

    @Override
    public BigDecimal getPhysicalStock(long materialId, StockQueryOptions options) {
        if (options == null)
            throw new NullPointerException("options");

        StockQueryResult result = getPhysicalStock(Arrays.asList(materialId), options);
        BigDecimal quantity = sumOfQuantity(result);
        return quantity;
    }

    @Override
    public BigDecimal getAvailableStock(long materialId, StockQueryOptions options) {
        if (options == null)
            throw new NullPointerException("options");

        StockQueryResult result = getAvailableStock(Arrays.asList(materialId), options);
        BigDecimal quantity = sumOfQuantity(result);
        return quantity;
    }

    @Override
    public BigDecimal getPlanQuantity(long materialId, StockQueryOptions options) {
        if (options == null)
            throw new NullPointerException("options");

        StockQueryResult result = getPlanSummary(Arrays.asList(materialId), options);
        BigDecimal quantity = sumOfQuantity(result);
        return quantity;
    }

    static BigDecimal sumOfQuantity(AbstractItemList<?> result) {
        BigDecimal sum = new BigDecimal(0);
        for (AbstractItem item : result) {
            BigDecimal quantity = item.getQuantity();
            sum = sum.add(quantity);
        }
        return sum;
    }

}
