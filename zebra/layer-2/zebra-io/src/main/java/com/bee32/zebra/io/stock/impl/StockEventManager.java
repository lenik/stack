package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 库存操作业务，如出、入库；调拨；委外加工等。
 * 
 * 库存作业由一系列库存操作构成。
 * 
 * @label 库存作业
 * 
 * @rel tag/?schema=12: 管理作业类型
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(StockEvent.class)
public class StockEventManager
        extends FooManager {

    public StockEventManager(IQueryable context) {
        super(context);
    }

}
