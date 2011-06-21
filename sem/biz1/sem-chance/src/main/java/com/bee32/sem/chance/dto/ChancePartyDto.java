package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.people.dto.PartyDto;

public class ChancePartyDto
        extends EntityDto<ChanceParty, Long> {

    private static final long serialVersionUID = 1L;

    private ChanceDto chance;
    private PartyDto party;
    private String role;

    public ChancePartyDto() {
        super();
    }

    public ChancePartyDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(ChanceParty source) {
        this.chance = marshal(ChanceDto.class, source.getChance(), false);
        this.party = marshal(PartyDto.class, source.getParty(), false);
        this.role = source.getRole();
    }

    @Override
    protected void _unmarshalTo(ChanceParty target) {
        merge(target, "chance", chance);
        merge(target, "party", party);
        target.setRole(role);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
