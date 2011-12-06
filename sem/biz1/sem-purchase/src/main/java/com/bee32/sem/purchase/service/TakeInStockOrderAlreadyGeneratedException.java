package com.bee32.sem.purchase.service;

public class TakeInStockOrderAlreadyGeneratedException extends Exception {

    private static final long serialVersionUID = 1L;

    public TakeInStockOrderAlreadyGeneratedException() {
        super("采购请求已经生成过采购入库单.");
    }
}
