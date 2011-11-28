package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SALE")
public class StockSale
        extends StockTrade {

    private static final long serialVersionUID = 1L;

    public StockSale() {
        this.subject = AccountSubject.s1131;    //销售入账单默认科目为应收账款
        this.debitSide = true;
    }

}
