package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.meta.decl.ObjectType;

import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 送货单明细
 * 
 * 送货单的具体明细项目
 * 
 * @rel sdoc/: 管理销售订单
 * @rel dldoc/: 管理送货单
 */
@ObjectType(DeliveryItem.class)
public class DeliveryItemIndex
        extends QuickIndex {

}
