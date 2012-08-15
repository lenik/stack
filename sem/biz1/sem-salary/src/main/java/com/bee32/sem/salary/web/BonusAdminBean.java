package com.bee32.sem.salary.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.BaseBonusDto;
import com.bee32.sem.salary.entity.BaseBonus;

public class BonusAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public BonusAdminBean() {
        super(BaseBonus.class, BaseBonusDto.class, 0);
    }

}
