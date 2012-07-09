package com.bee32.sem.account.dto;

import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountEdDto
        extends CurrentAccountDto {

    private static final long serialVersionUID = 1L;

    public AccountEdDto() {
        super();
    }

    public AccountEdDto(int mask) {
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
