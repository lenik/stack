package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.CurrentAccount;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class CurrentAccountDto
        extends ProcessEntityDto<CurrentAccount> {

    private static final long serialVersionUID = 1L;

    PartyDto party;
    MutableMCValue amount;

    OrgUnitDto orgUnit;
    PersonDto person;

    public CurrentAccountDto() {
        super();
    }

    public CurrentAccountDto(int mask) {
        super(mask);
    }

    @Override
    protected void _marshal(CurrentAccount source) {
        party = mref(PartyDto.class, source.getParty());
        amount = source.getAmount().toMutable();

        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());
        person = mref(PersonDto.class, source.getPerson());
    }

    @Override
    protected void _unmarshalTo(CurrentAccount target) {
        merge(target, "party", party);
        target.setAmount(amount);

        merge(target, "orgUnit", orgUnit);
        merge(target, "person", person);

    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
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
