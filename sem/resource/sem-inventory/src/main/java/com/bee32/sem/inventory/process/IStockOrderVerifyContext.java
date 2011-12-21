package com.bee32.sem.inventory.process;

import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;

public interface IStockOrderVerifyContext
        extends ISingleVerifierWithNumber {

    StockOrderSubject getSubject();

}
