package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 送货单
 * 
 * @label 送货单
 * 
 * @rel sdoc/: 管理销售订单
 * @rel org/?shipper=1: 管理承运人
 */
@ObjectType(Delivery.class)
public class DeliveryIndex
        extends QuickIndex {

    public DeliveryIndex(IQueryable context) {
        super(context);
    }

}
