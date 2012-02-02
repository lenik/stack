package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class BudgetRequestDto
        extends TxEntityDto<BudgetRequest> {

    private static final long serialVersionUID = 1L;

    String text;
    MutableMCValue value;
    AccountTicketDto ticket;

    @Override
    protected void _marshal(BudgetRequest source) {
        text = source.getText();
        value = source.getValue().toMutable();
        ticket = mref(AccountTicketDto.class, source.getTicket());
    }

    @Override
    protected void _unmarshalTo(BudgetRequest target) {
        target.setText(text);
        target.setValue(value);
        merge(target, "ticket", ticket);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MutableMCValue getValue() {
        return value;
    }

    public void setValue(MutableMCValue value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }

    public AccountTicketDto getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicketDto ticket) {
        this.ticket = ticket;
    }

    public String getCreator() {
        return this.getOwnerDisplayName();
    }

}
