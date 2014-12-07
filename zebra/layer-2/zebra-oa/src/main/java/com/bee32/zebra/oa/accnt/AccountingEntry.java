package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;

import com.tinylily.model.base.CoMomentInterval;

public class AccountingEntry
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int DEBIT_SIDE = 0;
    public static final int CREDIT_SIDE = 1;

    int side;
    Account subject;
    BigDecimal value = BigDecimal.ZERO;

    public Account getSubject() {
        return subject;
    }

    public void setSubject(Account subject) {
        this.subject = subject;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    /**
     * 借方
     */
    public boolean isDebitSide() {
        return side == DEBIT_SIDE;
    }

    /**
     * 贷方
     */
    public boolean isCreditSide() {
        return side == CREDIT_SIDE;
    }

    /**
     * 金额
     */
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }

}
