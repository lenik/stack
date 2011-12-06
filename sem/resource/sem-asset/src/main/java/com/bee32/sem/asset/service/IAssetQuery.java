package com.bee32.sem.asset.service;

public interface IAssetQuery {

    /**
     * 获取给定时间的某些科目的实际资产余量。
     *
     * @param options
     *            查询选项。
     * @return 以对应科目为根结点的余量树。
     * @see AssetQueryOptions
     */
    SumNode getSummary(AssetQueryOptions options);

}
