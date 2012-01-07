package com.bee32.sem.asset.entity;

public class SubjectForStockPurchaseWrongException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "销售入帐单的科目必须为[应付账款]或其子科目!";
    }

}
