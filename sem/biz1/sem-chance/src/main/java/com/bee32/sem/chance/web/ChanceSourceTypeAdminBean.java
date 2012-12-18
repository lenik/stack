package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ChanceSourceTypeDto;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChanceSourceTypeAdminBean
        extends SimpleEntityViewBean {
    private static final long serialVersionUID = 1L;

    public ChanceSourceTypeAdminBean() {
        super(ChanceSourceType.class, ChanceSourceTypeDto.class, 0);
    }

}
