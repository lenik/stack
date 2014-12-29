package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.tk.sea.FooManager;

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
        extends FooManager {

    public DeliveryManager(IQueryable context) {
        super(Delivery.class, context);
    }

}
