package com.bee32.zebra.erp.stock.impl;

import com.bee32.zebra.erp.stock.StockEvent;
import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class StockEventManager
        extends CoEntityManager {

    StockEventMapper mapper;

    public StockEventManager() {
        super(StockEvent.class);
        mapper = VhostDataService.getInstance().getMapper(StockEventMapper.class);
    }

    public StockEventMapper getMapper() {
        return mapper;
    }

}
