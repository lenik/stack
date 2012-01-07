package com.bee32.sem.asset.entity;

public class SubjectForStockSaleWrongException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "采购入帐单的科目必须为[应付账款]或其子科目!";
    }

}
