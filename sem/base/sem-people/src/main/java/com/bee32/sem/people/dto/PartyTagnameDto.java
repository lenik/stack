package com.bee32.sem.people.dto;

import com.bee32.plover.ox1.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.PartyTagname;

public class PartyTagnameDto
        extends SimpleNameDictDto<PartyTagname> {

    private static final long serialVersionUID = 1L;

    public PartyTagnameDto() {
        super();
    }

    public PartyTagnameDto(int fmask) {
        super(fmask);
    }

}
