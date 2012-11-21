package com.bee32.xem.zjhf.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.xem.zjhf.dto.MotorTypeDto;
import com.bee32.xem.zjhf.entity.MotorType;

@ForEntity(MotorType.class)
public class MotorTypeAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MotorTypeAdminBean() {
        super(MotorType.class, MotorTypeDto.class, 0);
    }
}
