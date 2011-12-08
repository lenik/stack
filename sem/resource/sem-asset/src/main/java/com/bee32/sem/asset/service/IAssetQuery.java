package com.bee32.sem.asset.service;

/**
 * <pre>
 *     查询返回的是结余树，假如查询条件中包含科目 110304，那么这棵树的形式如：
 *
 *     - 11
 *       - 1101
 *       - 1103
 *         - 110304
 *       - 1105
 *       ...
 *
 *     该树的根结点是顶层的科目，而不是要查询的科目，即使查询单个科目返回的也是从顶层科目开始的树。
 *
 *     结点类型为 SumNode，其中：
 *         children: 对应子科目的结点。
 *         items: 明细条目（仅该科目下的明细，不包括子科目的明细）
 *         total: 本地货币表示的结余（汇总该科目和所有子科目）
 *
 *     假如要查询单个科目：
 *         options = new AssetQueryOptions();
 *         options.addSubject(AccountSubject.s110304);
 *         options.setParties(null, true);
 *
 *         tree = assetQuery.getSummary(options);
 *
 *         SumNode node = tree.getNode(AccountSubject.s110304.getId());
 *         total = node.getTotal();
 *         items = node.getItems();
 * </pre>
 */
public interface IAssetQuery {

    /**
     * 获取给定时间的某些科目的实际资产余量。
     *
     * @param options
     *            查询选项。
     * @return 以对应科目为根结点的余量树。
     * @see AssetQueryOptions
     */
    SumTree getSummary(AssetQueryOptions options);

}
