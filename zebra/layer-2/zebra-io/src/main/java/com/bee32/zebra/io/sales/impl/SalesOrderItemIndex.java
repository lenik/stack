package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.sea.FooIndex;

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
        extends FooIndex {

    public SalesOrderItemIndex(IQueryable context) {
        super(context);
    }

}
