package com.bee32.sem.make.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.entity.MakeStepName;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(StockWarehouse.class)
public class MakeStepNameAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeStepNameAdminBean() {
        super(MakeStepName.class, MakeStepNameDto.class, 0);
    }

}
