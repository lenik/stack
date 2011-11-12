package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.world.monetary.MCValue;

public class AccountTicketItemDto
        extends TxEntityDto<AccountTicketItem> {

    private static final long serialVersionUID = 1L;

    int index;

    AccountSubjectDto subject;
    PartyDto party;

    MCValue value = new MCValue();

    boolean debitSide;
    AccountTicketDto ticket;

    @Override
    protected void _marshal(AccountTicketItem source) {
        index = source.getIndex();

        subject = mref(AccountSubjectDto.class, source.getSubject());
        party = mref(PartyDto.class, source.getParty());

        value = source.getValue();

        debitSide = source.isDebitSide();
        ticket = mref(AccountTicketDto.class, source.getTicket());
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem target) {
        target.setIndex(index);

        merge(target, "subject", subject);
        merge(target, "party", party);

        target.setValue(value);

        target.setDebitSide(debitSide);
        merge(target, "ticket", ticket);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AccountSubjectDto getSubject() {
        return subject;
    }

    public void setSubject(AccountSubjectDto subject) {
        this.subject = subject;
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        this.value = value;
    }

    public boolean isDebitSide() {
        return debitSide;
    }

    public void setDebitSide(boolean debitSide) {
        this.debitSide = debitSide;
    }

    public String getSideName() {
        if (debitSide)
            return "借方";
        else
            return "贷方";
    }

    public AccountTicketDto getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicketDto ticket) {
        this.ticket = ticket;
    }
}
