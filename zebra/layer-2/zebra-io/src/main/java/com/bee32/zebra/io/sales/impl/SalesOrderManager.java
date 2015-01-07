package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 销售订单
 * 
 * 企业与客户之间签订的销售协议，同时也是企业对客户的一种销售承诺；
 * 它上接销售合同，并向下传递至销售发货。通过订单信息的维护与管理，实现企业对销售的计划性控制，使企业的销售活动、生产活动、采购活动处于有序、流畅、高效的状态。
 * 
 * @label 销售订单
 * 
 * @rel topic/: 管理项目/机会
 * @rel dldoc/: 管理送货单
 */
public class SalesOrderManager
        extends FooManager {

    public SalesOrderManager(IQueryable context) {
        super(SalesOrder.class, context);
    }

}
