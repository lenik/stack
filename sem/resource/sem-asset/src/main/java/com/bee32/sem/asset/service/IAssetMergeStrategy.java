package com.bee32.sem.asset.service;

import java.io.Serializable;

import com.bee32.sem.asset.entity.AccountTicketItem;

public interface IAssetMergeStrategy
        extends Serializable {

    Object getMergeKey(AccountTicketItem item);

}
