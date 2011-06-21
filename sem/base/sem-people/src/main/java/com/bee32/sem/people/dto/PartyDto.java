package com.bee32.sem.people.dto;

import com.bee32.sem.people.entity.Party;

public class PartyDto
        extends AbstractPartyDto<Party> {

    private static final long serialVersionUID = 1L;

    public PartyDto() {
        super();
    }

    public PartyDto(int selection) {
        super(selection);
    }

}
