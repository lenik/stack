package com.bee32.sem.chance.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.people.dto.PartyDto;

public class ChancePartyDto
        extends CEntityDto<ChanceParty, Long> {

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
        this.chance = mref(ChanceDto.class, source.getChance());
        this.party = mref(PartyDto.class, source.getParty());
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

    @NLength(min = 2, max = ChanceParty.ROLE_LENGTH)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null)
            throw new NullPointerException("role");
        role = role.trim();
        this.role = role;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(chance), naturalId(party));
    }

}
