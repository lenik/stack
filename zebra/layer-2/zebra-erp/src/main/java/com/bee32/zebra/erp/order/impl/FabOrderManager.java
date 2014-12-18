package com.bee32.zebra.erp.order.impl;

import com.bee32.zebra.erp.order.FabOrder;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 生产订单（合同）。
 * 
 * @label 生产订单
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FabOrderManager
        extends FooManager {

    FabOrderMapper mapper;

    public FabOrderManager() {
        super(FabOrder.class);
        mapper = VhostDataService.getInstance().getMapper(FabOrderMapper.class);
    }

    public FabOrderMapper getMapper() {
        return mapper;
    }

}
