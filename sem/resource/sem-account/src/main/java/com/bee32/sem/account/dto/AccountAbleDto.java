package com.bee32.sem.account.dto;

import java.util.Date;

import com.bee32.sem.account.entity.AccountAble;
import com.bee32.sem.account.entity.CurrentAccount;

public class AccountAbleDto
        extends CurrentAccountDto {

    private static final long serialVersionUID = 1L;

    Date expectedDate;

    public AccountAbleDto() {
        super();
    }

    public AccountAbleDto(int mask) {
        super(mask);
    }

    @Override
    protected void _marshal(CurrentAccount source) {
        super._marshal(source);

        AccountAble o = (AccountAble) source;

        expectedDate = o.getExpectedDate();
    }

    @Override
    protected void _unmarshalTo(CurrentAccount target) {
        super._unmarshalTo(target);

        AccountAble o = (AccountAble) target;

        o.setExpectedDate(expectedDate);
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }
}
