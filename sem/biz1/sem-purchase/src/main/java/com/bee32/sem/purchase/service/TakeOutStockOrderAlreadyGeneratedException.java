package com.bee32.sem.purchase.service;

public class TakeOutStockOrderAlreadyGeneratedException extends Exception {

    private static final long serialVersionUID = 1L;

    public TakeOutStockOrderAlreadyGeneratedException() {
        super("送货单已经生成过销售出库单.");
    }
}
