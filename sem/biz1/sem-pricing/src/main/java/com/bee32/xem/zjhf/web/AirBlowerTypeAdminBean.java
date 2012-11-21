package com.bee32.xem.zjhf.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.xem.zjhf.dto.AirBlowerTypeDto;
import com.bee32.xem.zjhf.entity.AirBlowerType;

@ForEntity(AirBlowerType.class)
public class AirBlowerTypeAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AirBlowerTypeAdminBean() {
        super(AirBlowerType.class, AirBlowerTypeDto.class, 0);
    }
}
