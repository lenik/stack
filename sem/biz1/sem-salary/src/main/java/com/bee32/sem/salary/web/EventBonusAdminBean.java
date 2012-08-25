package com.bee32.sem.salary.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.EventBonusDto;
import com.bee32.sem.salary.entity.EventBonus;

public class EventBonusAdminBean
        extends SimpleEntityViewBean {

    public EventBonusAdminBean() {
        super(EventBonus.class, EventBonusDto.class, 0);
    }

    private static final long serialVersionUID = 1L;

}
