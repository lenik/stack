package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 送货单
 * 
 * @label 送货单
 * 
 * @rel sdoc/: 管理销售订单
 * @rel org/?shipper=1: 管理承运人
 */
@PathToken("dldoc")
public class DeliveryManager
        extends CoEntityManager {

    DeliveryMapper mapper;

    public DeliveryManager() {
        super(Delivery.class);
        mapper = VhostDataService.getInstance().getMapper(DeliveryMapper.class);
    }

    public DeliveryMapper getMapper() {
        return mapper;
    }

}
