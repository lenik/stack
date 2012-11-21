package com.bee32.xem.zjhf.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.xem.zjhf.dto.ValveTypeDto;
import com.bee32.xem.zjhf.entity.ValveType;

@ForEntity(ValveType.class)
public class ValveTypeAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ValveTypeAdminBean() {
        super(ValveType.class, ValveTypeDto.class, 0);
    }
}
