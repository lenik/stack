package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.world.monetary.MCValue;

public class BudgetRequestDto extends TxEntityDto<BudgetRequest> {

    private static final long serialVersionUID = 1L;

    String text;
    MCValue value;
    AccountDto account;

    @Override
    protected void _marshal(BudgetRequest source) {
        text = source.getText();
        value = source.getValue();
        account = mref(AccountDto.class, source.getAccount());
    }

    @Override
    protected void _unmarshalTo(BudgetRequest target) {
        target.setText(text);
        target.setValue(value);
        merge(target, "account", account);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        this.value = value;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }
}
