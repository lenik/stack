package com.bee32.sem.inventory.service;

import java.util.Date;

import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockSnapshot;

public interface IStockManager {

    /**
     * 提交库存的当前状态，形成下一个库存快照作为基准库存。
     */
    StockSnapshot commit(StockInventory inventory);

    /**
     * 和提交 {@link StockInventory#MAIN 总库存} 等价。
     *
     * @throws IllegalStateException
     *             如果总库存不存在。
     * @deprecated 建议调用 {@link #commit(StockInventory)} 方法。
     */
    @Deprecated
    StockSnapshot commit();

    /**
     * 获取当前的基准库存。
     */
    StockSnapshot getWorkingBase(StockInventory inventory);

    /**
     * 获取指定历史时刻的有效库存快照。
     */
    StockSnapshot getHistoryBase(Date date);

}
