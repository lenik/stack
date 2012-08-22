package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.NoteBalancing;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class NoteBalancingDto
        extends ProcessEntityDto<NoteBalancing> {

    private static final long serialVersionUID = 1L;

    NoteDto note;

    MutableMCValue amount;
    MutableMCValue interest;
    MutableMCValue cost;

    public NoteBalancingDto() {
        super();
    }

    public NoteBalancingDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(NoteBalancing source) {
        note = mref(NoteDto.class, source.getNote());
        amount = source.getAmount().toMutable();
        interest = source.getInterest().toMutable();
        cost = source.getCost().toMutable();

    }

    @Override
    protected void _unmarshalTo(NoteBalancing target) {
        merge(target, "note", note);
        target.setAmount(amount);
        target.setInterest(interest);
        target.setCost(cost);

    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();

    }

    public NoteDto getNote() {
        return note;
    }

    public void setNote(NoteDto note) {
        this.note = note;
    }

    public MutableMCValue getAmount() {
        return amount;
    }

    public void setAmount(MutableMCValue amount) {
        this.amount = amount;
    }

    public MutableMCValue getInterest() {
        return interest;
    }

    public void setInterest(MutableMCValue interest) {
        this.interest = interest;
    }

    public MutableMCValue getCost() {
        return cost;
    }

    public void setCost(MutableMCValue cost) {
        this.cost = cost;
    }

    public String getBalancingType() {
        if (this instanceof BillDiscountDto) return "贴现";
        if (this instanceof EndorsementDto) return "背书";
        if (this instanceof BalancingDto) return "结算";

        return "";
    }

}
