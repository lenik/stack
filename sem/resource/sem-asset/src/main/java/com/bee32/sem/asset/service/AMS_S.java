package com.bee32.sem.asset.service;

import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;

public class AMS_S
        implements IAssetMergeStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public AccountSubject getMergeKey(AccountTicketItem item) {
        return item.getSubject();
    }

    public static final AMS_S INSTANCE = new AMS_S();

}
