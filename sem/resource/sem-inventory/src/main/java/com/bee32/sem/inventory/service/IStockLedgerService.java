package com.bee32.sem.inventory.service;

import java.util.Date;

import com.bee32.sem.inventory.entity.StockItemList;

public interface IStockLedgerService {

    /**
     * 获取当前库存的存量。
     *
     * @return 当前库存的各物料存量列表。
     */
    StockItemList getLatestStockState();

    /**
     * 获取库存的历史状态。
     * <p>
     * 如果指定的时间超出库存历史时间范围，（比库存历史的最初时间还早、或比库存历史的最终时间还晚），则返回 <code>null</code>。
     * <p>
     * 当前时间不再库存历史的时间范围内，因此查询当前库存不能用本方法，必须用 {@link #getLatestStockState(Date)} 来返回当前库存。
     *
     * @param date
     *            历史时间点
     * @return 指定时间点的库存中各物料存量列表。
     */
    StockItemList getHistoryStockState(Date date);

    /**
     * 获取库存给定时间段的存量变化。
     *
     * @param fullSpan
     *            是否查询所有库存历史，如果为 <code>false</code>，则只查询当前期的（自从上次结转后发生的数据）；如果为 <code>true</code>
     *            则查询所有期间的数据。（效率较低）
     * @return 存量变化清单。清单中不包括没有发生变化的物料，但如果某物料发生过变化而变化的累计量为0，则清单中亦包括该物料项目。
     */
    StockItemList getStockChange(Date begin, Date end, boolean fullSpan);

}
