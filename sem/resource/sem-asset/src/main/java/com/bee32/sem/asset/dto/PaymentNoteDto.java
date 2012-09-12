package com.bee32.sem.asset.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.people.dto.PersonDto;

public class PaymentNoteDto extends FundFlowDto {

    private static final long serialVersionUID = 1L;

    PersonDto whoPay;

    public PaymentNoteDto() {
        super();
    }

    @Override
    protected void _marshal(FundFlow _source) {
        super._marshal(_source);

        PaymentNote source = (PaymentNote)_source;

        whoPay = mref(PersonDto.class, source.getWhoPay());
    }

    @Override
    protected void _unmarshalTo(FundFlow _target) {
        super._unmarshalTo(_target);

        PaymentNote target = (PaymentNote)_target;

        merge(target, "whoPay", whoPay);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
    }

    public PersonDto getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(PersonDto whoPay) {
        this.whoPay = whoPay;
    }


}
