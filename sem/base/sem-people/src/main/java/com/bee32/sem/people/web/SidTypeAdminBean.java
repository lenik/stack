package com.bee32.sem.people.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PartySidTypeDto;
import com.bee32.sem.people.entity.PartySidType;

public class SidTypeAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SidTypeAdminBean() {
        super(PartySidType.class, PartySidTypeDto.class, 0);
    }

}
