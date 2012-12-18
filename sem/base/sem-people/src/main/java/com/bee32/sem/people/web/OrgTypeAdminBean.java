package com.bee32.sem.people.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.entity.OrgType;

public class OrgTypeAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public OrgTypeAdminBean() {
        super(OrgType.class, OrgTypeDto.class, 0);
    }

}
