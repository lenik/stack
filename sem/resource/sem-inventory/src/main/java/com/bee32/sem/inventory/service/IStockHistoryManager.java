package com.bee32.sem.inventory.service;

import java.util.Date;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockSnapshot;

public interface IStockHistoryManager {

    StockSnapshot compact();

    StockSnapshot getWorkingBase();

    StockOrder getWorkingLedger();

    StockSnapshot getHistoryBase(Date date);

}
