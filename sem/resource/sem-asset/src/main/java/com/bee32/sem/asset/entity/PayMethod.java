package com.bee32.sem.asset.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "pay_method_seq", allocationSize = 1)
public class PayMethod
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public PayMethod() {
        super();
    }

    public PayMethod(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    public PayMethod(int order, String name, String label) {
        super(order, name, label);
    }

    public PayMethod(String name, String label, String description) {
        super(name, label, description);
    }

    public PayMethod(String name, String label) {
        super(name, label);
    }

    public static PayMethod PAY_MONEY = new PayMethod("PAY", "现金支付");
    public static PayMethod BANK_TRANSFER = new PayMethod("BTX", "银行转帐");

}
