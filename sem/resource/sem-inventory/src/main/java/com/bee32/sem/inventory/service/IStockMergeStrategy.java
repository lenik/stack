package com.bee32.sem.inventory.service;

import java.io.Serializable;

import com.bee32.sem.inventory.entity.StockOrderItem;

public interface IStockMergeStrategy
        extends Serializable {

    Object getMergeKey(StockOrderItem item);

}
