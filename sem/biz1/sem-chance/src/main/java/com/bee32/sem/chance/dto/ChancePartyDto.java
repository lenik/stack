package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.BlueEntityDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.people.entity.Party;

public class ChancePartyDto
        extends BlueEntityDto<ChanceParty, Long> {

    private static final long serialVersionUID = 1L;

    private Chance chance;
    private Party party;
    private String role;

    public ChancePartyDto() {
        super();
    }

    public ChancePartyDto(ChanceParty source) {
        super(source);
    }

    public ChancePartyDto(int selection, ChanceParty source) {
        super(selection, source);
    }

    public ChancePartyDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(ChanceParty source) {
    }

    @Override
    protected void _unmarshalTo(ChanceParty target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
