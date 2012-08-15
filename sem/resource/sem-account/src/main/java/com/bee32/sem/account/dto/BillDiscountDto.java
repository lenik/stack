package com.bee32.sem.account.dto;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.sem.account.entity.BillDiscount;
import com.bee32.sem.account.entity.NoteBalancing;

public class BillDiscountDto
        extends NoteBalancingDto {

    private static final long serialVersionUID = 1L;

    String bank;
    float rate;

    @Override
    protected void _marshal(NoteBalancing source) {
        super._marshal(source);

        BillDiscount o = (BillDiscount) source;

        bank = o.getBank();
        rate = o.getRate();
    }

    @Override
    protected void _unmarshalTo(NoteBalancing target) {
        super._unmarshalTo(target);

        BillDiscount o = (BillDiscount) target;

        o.setBank(bank);
        o.setRate(rate);
    }

    @NLength(min = 2, max = BillDiscount.BANK_LENGTH)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
