package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import org.apache.commons.lang.StringUtils;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountTicketItemDto
        extends ProcessEntityDto<AccountTicketItem>
        implements IVerifiableDto, IEnclosedObject<Object> {

    private static final long serialVersionUID = 1L;

    AccountTicketDto ticket;
    int index;

    AccountSubjectDto subject;
    PartyDto party;
    PersonDto person;
    OrgUnitDto orgUnit;

    boolean debitSide;
    MutableMCValue value;

    String pattern;

    public AccountTicketItemDto() {
        super();
    }

    public AccountTicketItemDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _copy() {
        super._copy();
        value = value.clone();
    }

    @Override
    protected void _marshal(AccountTicketItem source) {
        ticket = mref(AccountTicketDto.class, source.getTicket());
        index = source.getIndex();

        setSubject(mref(AccountSubjectDto.class, source.getSubject()));
        party = mref(PartyDto.class, source.getParty());
        person = mref(PersonDto.class, source.getPerson());
        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());

        debitSide = source.isDebitSide();
        if (isNegative())
            value = source.getValue().negate().toMutable();
        else
            value = source.getValue().toMutable();
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem target) {
        merge(target, "ticket", ticket);
        target.setIndex(index);

        merge(target, "subject", subject);
        merge(target, "party", party);
        merge(target, "person", person);
        merge(target, "orgUnit", orgUnit);

        target.setDebitSide(debitSide);
        if (isNegative())
            target.setValue(value.negate());
        else
            target.setValue(value);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public AccountTicketDto getEnclosingObject() {
        return getTicket();
    }

    @Override
    public void setEnclosingObject(Object enclosingObject) {
        AccountTicketDto ticket = (AccountTicketDto) enclosingObject;
        setTicket(ticket);

        if(ticket.getItems().size() > 0) {
            this.setDescription(ticket.getItems().get(ticket.getItems().size() - 1).getDescription());
        }
    }

    public AccountTicketDto getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicketDto ticket) {
        this.ticket = ticket;
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
        pattern = null;

    }

    public boolean isNegative() {
        if (subject == null) {
            throw new NullPointerException("subject");
        }

        if (debitSide) {
            // 当前凭证条目为借方
            if (subject.debitSign) {
                // 当前科目为"借方时为正数(增加)"
                return false;
            } else {
                // 当前科目为"借方时为负数(减少)"
                return true;
            }
        } else {
            // 当前凭证条目为贷方
            if (subject.creditSign) {
                // 当前科目为"贷方时为正数(增加)"
                return false;
            } else {
                // 当前科目为"贷方时为负数(减少)"
                return true;
            }
        }
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public OrgUnitDto getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDto orgUnit) {
        this.orgUnit = orgUnit;
    }

    public MutableMCValue getValue() {
        return value;
    }

    public void setValue(MutableMCValue value) {
        if (value == null)
            throw new NullPointerException("value");
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

    public String getCreator() {
        UserDto owner = getOwner();
        if (owner == null)
            return "(n/a)";
        else
            return owner.getDisplayName();
    }

    public String getPattern() {
        if (StringUtils.isEmpty(pattern)) {
            if(!DTOs.isNull(subject)) {
                pattern = subject.getLabel();
            } else {
                pattern = "";
            }
        }

        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
