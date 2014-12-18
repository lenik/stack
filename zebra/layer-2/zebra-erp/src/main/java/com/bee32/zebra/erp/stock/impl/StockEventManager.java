package com.bee32.zebra.erp.stock.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.erp.stock.StockEvent;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

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
@PathToken("stdoc")
public class StockEventManager
        extends FooManager {

    StockEventMapper mapper;

    public StockEventManager() {
        super(StockEvent.class);
        mapper = VhostDataService.getInstance().getMapper(StockEventMapper.class);
    }

    public StockEventMapper getMapper() {
        return mapper;
    }

}
