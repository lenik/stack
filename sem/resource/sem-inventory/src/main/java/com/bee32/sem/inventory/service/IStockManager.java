package com.bee32.sem.inventory.service;

import java.util.Date;

import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.entity.StockPeriodType;
import com.bee32.sem.world.monetary.FxrQueryException;

public interface IStockManager {

    /**
     * 提交库存的当前状态，形成下一个库存快照作为基准库存。
     *
     * @param inventory
     *            逻辑库存
     * @param periodType
     *            快照类型
     * @param description
     *            快照描述
     * @return 新形成的快照，此快照同时作为逻辑库存的新的基准库存。
     */
    StockPeriod pack(StockInventory inventory, StockPeriodType periodType, String description)
            throws FxrQueryException;

    /**
     * 和提交 {@link StockInventory#MAIN 主逻辑库存} 等价。
     *
     * @param snapshotType
     *            快照类型
     * @param description
     *            快照描述
     * @return 新形成的快照，此快照同时作为主逻辑库存的新的基准库存。
     * @throws IllegalStateException
     *             如果主逻辑库存不存在。
     * @deprecated 建议调用 {@link #commit(StockInventory)} 方法。
     * @see StockInventory#MAIN
     */
    @Deprecated
    StockPeriod pack(StockPeriodType snapshotType, String description)
            throws FxrQueryException;

    /**
     * 获取当前的基准库存。
     */
    StockPeriod getWorkingBase(StockInventory inventory);

    /**
     * 获取指定历史时刻的有效库存快照。
     */
    StockPeriod getHistoryBase(Date date);

}
