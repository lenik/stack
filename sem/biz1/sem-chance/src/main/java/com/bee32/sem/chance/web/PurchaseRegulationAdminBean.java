package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.PurchaseRegulationDto;
import com.bee32.sem.chance.entity.PurchaseRegulation;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PurchaseRegulationAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PurchaseRegulationAdminBean() {
        super(PurchaseRegulation.class, PurchaseRegulationDto.class, 0);
    }

}
