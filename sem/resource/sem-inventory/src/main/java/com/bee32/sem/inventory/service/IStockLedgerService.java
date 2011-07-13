package com.bee32.sem.inventory.service;

import java.util.Date;

public interface IStockLedgerService {

    StockLedger queryLedger(Date from, Date to);

}
