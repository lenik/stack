package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SALE")
public class StockSale
        extends StockTrade {

    private static final long serialVersionUID = 1L;

    public StockSale() {
        this.subject = predefined(AccountSubjects.class).s1131; // 销售入账单默认科目为应收账款
        this.debitSide = true;
    }

    @Override
    public void setSubject(AccountSubject subject) {
        if (!subject.getName().startsWith("1131"))
            throw new IllegalArgumentException("采购入帐单的科目必须为[应付账款]或其子科目!");
        super.setSubject(subject);
    }

}
