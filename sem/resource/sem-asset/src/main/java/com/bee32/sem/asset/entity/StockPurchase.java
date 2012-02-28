package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PURC")
public class StockPurchase
        extends StockTrade {

    private static final long serialVersionUID = 1L;

    public StockPurchase() {
        this.subject = predefined(AccountSubjects.class).s2121; // 采购入账单默认科目为应付账款
        this.debitSide = false;
    }

    @Override
    public void setSubject(AccountSubject subject) {
        if (!subject.getName().startsWith("2121"))
            throw new IllegalArgumentException("销售入帐单的科目必须为[应付账款]或其子科目!");
        super.setSubject(subject);
    }

}
