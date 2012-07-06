package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.AccountReceive;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountReceiveDto
        extends ProcessEntityDto<AccountReceive> {

    private static final long serialVersionUID = 1L;

    PartyDto customer;
    MutableMCValue amount;

    OrgUnitDto orgUnit;
    PersonDto person;

    public AccountReceiveDto() {
        super();
    }

    public AccountReceiveDto(int mask) {
        super(mask);
    }

    @Override
    protected void _marshal(AccountReceive source) {
        customer = mref(PartyDto.class, source.getCustomer());
        amount = source.getAmount().toMutable();

        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());
        person = mref(PersonDto.class, source.getPerson());
    }

    @Override
    protected void _unmarshalTo(AccountReceive target) {
        merge(target, "customer", customer);
        target.setAmount(amount);

        merge(target, "orgUnit", orgUnit);
        merge(target, "person", person);

    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        this.customer = customer;
    }

    public MutableMCValue getAmount() {
        return amount;
    }

    public void setAmount(MutableMCValue amount) {
        this.amount = amount;
    }

    public OrgUnitDto getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDto orgUnit) {
        this.orgUnit = orgUnit;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

}
