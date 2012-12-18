package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ProcurementMethodDto;
import com.bee32.sem.chance.entity.ProcurementMethod;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ProcurementMethodAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ProcurementMethodAdminBean() {
        super(ProcurementMethod.class, ProcurementMethodDto.class, 0);
    }

}
