package com.bee32.sem.wage.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.wage.dto.BaseBonusDto;
import com.bee32.sem.wage.entity.BaseBonus;

public class BonusAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public BonusAdminBean() {
        super(BaseBonus.class, BaseBonusDto.class, 0);
    }

}
