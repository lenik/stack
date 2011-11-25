package com.bee32.sem.asset.service;

import com.bee32.sem.asset.entity.AccountTicket;

public interface IAssetQuery {

    /**
     * 获取给定时间的某些科目的实际资产余量。
     *
     * @param options
     *            查询选项。
     * @return 对应科目的余量。
     * @see AssetQueryOptions
     */
    AccountTicket getSummary(AssetQueryOptions options);

}
