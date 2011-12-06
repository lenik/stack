package com.bee32.sem.purchase.service;

public class AdviceHaveNotVerifiedException extends Exception {

    private static final long serialVersionUID = 1L;

    public AdviceHaveNotVerifiedException() {
        super("采购建议没有通过审核.");
    }
}
