package com.bee32.zebra.oa.model.accnt;

public class AccountLine {

    public static final int DEBIT_SIDE = 0;
    public static final int CREDIT_SIDE = 1;

    long id;
    int side;
    AccountSubject subject;
    double value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountSubject getSubject() {
        return subject;
    }

    public void setSubject(AccountSubject subject) {
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
