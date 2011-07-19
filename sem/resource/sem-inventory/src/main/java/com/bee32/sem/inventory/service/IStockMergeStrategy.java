package com.bee32.sem.inventory.service;

import com.bee32.sem.inventory.entity.StockOrderItem;

public interface IStockMergeStrategy {

    Object getMergeKey(StockOrderItem item);

}
