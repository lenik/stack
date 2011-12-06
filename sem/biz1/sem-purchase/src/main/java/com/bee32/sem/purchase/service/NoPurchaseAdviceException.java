package com.bee32.sem.purchase.service;

public class NoPurchaseAdviceException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoPurchaseAdviceException() {
        super("采购请求项目没有对应的采购建议.");
    }
}
