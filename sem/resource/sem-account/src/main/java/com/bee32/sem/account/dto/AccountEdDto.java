package com.bee32.sem.account.dto;

import com.bee32.sem.account.entity.CurrentAccount;

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
    protected void _marshal(CurrentAccount source) {
        super._marshal(source);

        // 收款或付款数字取反，以减少应收或应付金额
        amount = source.getAmount().negate().toMutable();
    }

    @Override
    protected void _unmarshalTo(CurrentAccount target) {
        super._unmarshalTo(target);

        // 收款或付款数字取反，以减少应收或应付金额
        target.setAmount(target.getAmount().negate());
    }
}
