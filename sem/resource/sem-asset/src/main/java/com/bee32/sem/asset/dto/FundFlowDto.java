package com.bee32.sem.asset.dto;

import java.math.BigDecimal;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MutableMCValue;

public class FundFlowDto
        extends MomentIntervalDto<FundFlow> {

    private static final long serialVersionUID = 1L;

    PersonDto operator;
    String text;
    MutableMCValue value;
    AccountTicketDto ticket;

    BigDecimal nativeValue;

    @Override
    protected void _marshal(FundFlow source) {
        operator = mref(PersonDto.class, source.getOperator());
        text = source.getText();
        value = source.getValue().toMutable();
        ticket = mref(AccountTicketDto.class, source.getTicket());
    }

    @Override
    protected void _unmarshalTo(FundFlow target) {
        merge(target, "operator", operator);
        target.setText(text);
        target.setValue(value);
        merge(target, "ticket", ticket);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PersonDto getOperator() {
        return operator;
    }

    public void setOperator(PersonDto operator) {
        this.operator = operator;
    }

    @NLength(min = 1, max = FundFlow.TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = TextUtil.normalizeSpace(text);
    }

    public MutableMCValue getValue() {
        return value;
    }

    public void setValue(MutableMCValue value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
        nativeValue = null;
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

    public BigDecimal getNativeValue()
            throws FxrQueryException {
        if (nativeValue == null) {
            nativeValue = value.getNativeValue(getCreatedDate());
        }
        return nativeValue;
    }

}
