package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ChancePriorityDto;
import com.bee32.sem.chance.entity.ChancePriority;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChancePriorityBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ChancePriorityBean() {
        super(ChancePriority.class, ChancePriorityDto.class, 0);
    }

}
