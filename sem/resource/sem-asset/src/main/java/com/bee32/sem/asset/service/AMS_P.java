package com.bee32.sem.asset.service;

import com.bee32.sem.asset.entity.AccountTicketItem;

public class AMS_P
        implements IAssetMergeStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getMergeKey(AccountTicketItem item) {
        return item.getParty();
    }

    public static final AMS_P INSTANCE = new AMS_P();

}
