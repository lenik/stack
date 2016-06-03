package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.meta.decl.ObjectType;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 订单项
 * 
 * 销售订单的具体项目。
 * 
 * @label 销售订单项
 * 
 * @rel sdoc/: 管理销售订单
 * @rel dldoc/: 管理送货单
 */
@ObjectType(SalesOrderItem.class)
public class SalesOrderItemIndex
        extends QuickIndex<SalesOrderItem> {

}
