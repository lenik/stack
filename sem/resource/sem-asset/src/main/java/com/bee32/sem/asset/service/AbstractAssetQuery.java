package com.bee32.sem.asset.service;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.util.AssetCriteria;

public abstract class AbstractAssetQuery
        extends DataService
        implements IAssetQuery {

    @Override
    public AccountTicket getSummary(AssetQueryOptions options) {
        ICriteriaElement selection = AssetCriteria.select(options);
        AccountTicket summary = getSummary(selection, options);
        summary.setLabel("【汇总】资产汇总清单");
        return summary;
    }

    public abstract AccountTicket getSummary(ICriteriaElement selection, AssetQueryOptions options);

}
