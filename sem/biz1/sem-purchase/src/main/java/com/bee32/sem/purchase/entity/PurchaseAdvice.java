package com.bee32.sem.purchase.entity;

import com.bee32.sem.base.tx.TxEntity;

public class PurchaseAdvice extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int REASON_LENGTH = 500;

    Inquiry preferredInquiry;
    String reason;


}
