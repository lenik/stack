package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ActionStyleAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ActionStyleAdminBean() {
        super(ChanceActionStyle.class, ChanceActionStyleDto.class, 0);
    }

}
