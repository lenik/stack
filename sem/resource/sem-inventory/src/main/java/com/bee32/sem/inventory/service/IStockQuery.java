package com.bee32.sem.inventory.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;

public interface IStockQuery {

    /**
     * 获取当前库存的存量。
     *
     * @return 当前库存的各物料存量列表。
     */
    StockItemList getCurrentStockState();

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

    /**
     * 获取给定时间的某些物料的实际库存余量。
     *
     * @param cbatch
     *            指定批号， <code>null</code> 表示不限批号。
     * @param location
     *            指定库位，<code>null</code> 表示所有库位。
     * @param warehouse
     *            指定库位，<code>null</code> 表示所有仓库。
     * @return 对应物料的余量。
     */
    StockItemList getActualSummary(List<Material> materials, StockQueryOptions options);

    /**
     * 获取给定时间的某单一物料的实际库存余量，不考虑批号和库位。
     *
     * @param cbatch
     *            指定批号， <code>null</code> 表示不限批号。
     * @param location
     *            指定库位，<code>null</code> 表示所有库位。
     * @param warehouse
     *            指定库位，<code>null</code> 表示所有仓库。
     * @return 对应物料的余量。
     */
    BigDecimal getActualQuantity(Material material, StockQueryOptions options);

    /**
     * 获取给定时间的某单一物料的可用库存余量（或曰逻辑库存余量），不考虑批号和库位。
     *
     * @param cbatch
     *            指定批号， <code>null</code> 表示不限批号。
     * @param location
     *            指定库位，<code>null</code> 表示所有库位。
     * @param warehouse
     *            指定库位，<code>null</code> 表示所有仓库。
     * @return 对应物料的余量。
     */
    StockItemList getVirtualSummary(List<Material> materials, StockQueryOptions options);

    /**
     * 获取给定时间的某单一物料的可用库存余量（或曰逻辑库存余量），不考虑批号和库位。
     *
     * @param cbatch
     *            指定批号， <code>null</code> 表示不限批号。
     * @param location
     *            指定库位，<code>null</code> 表示所有库位。
     * @param warehouse
     *            指定库位，<code>null</code> 表示所有仓库。
     * @return 对应物料的余量。
     */
    BigDecimal getVirtualQuantity(Material material, StockQueryOptions options);

    /**
     * 获取给定时间的某单一物料的“锁定”的库存数量（或曰计划数量），不考虑批号和库位。
     *
     * @param cbatch
     *            指定批号， <code>null</code> 表示不限批号。
     * @param location
     *            指定库位，<code>null</code> 表示所有库位。
     * @param warehouse
     *            指定库位，<code>null</code> 表示所有仓库。
     * @return 对应物料的数量。
     */
    StockItemList getPlanSummary(List<Material> materials, StockQueryOptions options);

    /**
     * 获取给定时间的某单一物料的"锁定“的库存数量（或曰计划数量），不考虑批号和库位。
     *
     * @param cbatch
     *            指定批号， <code>null</code> 表示不限批号。
     * @param location
     *            指定库位，<code>null</code> 表示所有库位。
     * @param warehouse
     *            指定库位，<code>null</code> 表示所有仓库。
     * @return 对应物料的数量。
     */
    BigDecimal getPlanQuantity(Material material, StockQueryOptions options);

}
