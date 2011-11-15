package com.bee32.sem.asset.dto;

import java.math.BigDecimal;
import java.util.Currency;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.world.monetary.MCValue;

public class BudgetRequestDto
        extends TxEntityDto<BudgetRequest> {

    private static final long serialVersionUID = 1L;

    String text;
    MCValue value;
    AccountTicketDto ticket;

    @Override
    protected void _marshal(BudgetRequest source) {
        text = source.getText();
        value = source.getValue();
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

    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        this.value = value;
    }

    public BigDecimal getValueDigit() {
        return value.getValue();
    }

    public void setValueDigit(BigDecimal valueDigit) {
        value = new MCValue(value.getCurrency(), valueDigit);
    }

    public String getValueCurrency() {
        if (value.getCurrency() == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return value.getCurrency().getCurrencyCode();
    }

    public void setValueCurrency(String currencyCode) {
        value = new MCValue(Currency.getInstance(currencyCode), value.getValue());
    }

    public AccountTicketDto getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicketDto ticket) {
        this.ticket = ticket;
    }

}
