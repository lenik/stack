package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;

import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.meta.cache.Derived;

import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.IdType;

@IdType(Long.class)
@TableName("acentry")
public class AccountingEntry
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;

    public static final int DEBIT_SIDE = 0;
    public static final int CREDIT_SIDE = 1;

    Account account;
    AccountingEvent event;
    boolean debitSide;
    BigDecimal value = BigDecimal.ZERO;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountingEvent getEvent() {
        return event;
    }

    public void setEvent(AccountingEvent event) {
        this.event = event;
    }

    /**
     * 借方
     */
    @Derived
    public boolean isDebitSide() {
        return value.signum() == 1;
    }

    /**
     * 贷方
     */
    @Derived
    public boolean isCreditSide() {
        return !isDebitSide();
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
