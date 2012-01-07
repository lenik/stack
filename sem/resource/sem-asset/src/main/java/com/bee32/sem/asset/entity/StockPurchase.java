package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PURC")
public class StockPurchase
        extends StockTrade {

    private static final long serialVersionUID = 1L;

    public StockPurchase() {
        this.subject = AccountSubject.s2121;    //采购入账单默认科目为应付账款
        this.debitSide = false;
    }

    @Override
    public void setSubject(AccountSubject subject) {
        if(!subject.getName().substring(0, 4).equals(AccountSubject.s2121.getName())) {
            throw new SubjectForStockPurchaseWrongException();
        }
        super.setSubject(subject);
    }
}
