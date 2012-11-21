package com.bee32.xem.zjhf.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.xem.zjhf.dto.AirBlowerBodyPriceDto;
import com.bee32.xem.zjhf.entity.AirBlowerBodyPrice;

@ForEntity(AirBlowerBodyPrice.class)
public class AirBlowerBodyPriceAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AirBlowerBodyPriceAdminBean() {
        super(AirBlowerBodyPrice.class, AirBlowerBodyPriceDto.class, 0);
    }
}
