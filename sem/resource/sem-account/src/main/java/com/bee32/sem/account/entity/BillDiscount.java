package com.bee32.sem.account.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 票据贴现
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("DISC")
public class BillDiscount extends NoteBalancing {

    private static final long serialVersionUID = 1L;

    public static final int BANK_LENGTH = 100;

    String bank;
    float rate;

    /**
     * 贴现银行
     * @return
     */
    @Column(length = BANK_LENGTH)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 贴现率
     * @return
     */
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

}
