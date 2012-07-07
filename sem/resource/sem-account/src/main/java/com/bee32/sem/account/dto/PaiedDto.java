package com.bee32.sem.account.dto;

import com.bee32.sem.world.monetary.MutableMCValue;

public class PaiedDto
        extends AccountReceivePayDto {

    private static final long serialVersionUID = 1L;

    public PaiedDto() {
        super();
    }

    public PaiedDto(int mask) {
        super(mask);
    }

    @Override
    public MutableMCValue getAmount() {
        return super.getAmount().negate().toMutable();
    }

    @Override
    public void setAmount(MutableMCValue amount) {
        super.setAmount(amount.negate().toMutable()); // 收款数字取反，以减少应收金额
    }
}
