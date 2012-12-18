package com.bee32.sem.people.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.entity.PartyTagname;

public class TagnameAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public TagnameAdminBean() {
        super(PartyTagname.class, PartyTagnameDto.class, 0);
    }

}
