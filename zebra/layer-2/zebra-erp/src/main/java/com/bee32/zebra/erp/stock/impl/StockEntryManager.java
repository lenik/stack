package com.bee32.zebra.erp.stock.impl;

import com.bee32.zebra.erp.stock.StockEntry;
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
public class StockEntryManager
        extends CoEntityManager {

    StockEntryMapper mapper;

    public StockEntryManager() {
        super(StockEntry.class);
        mapper = VhostDataService.getInstance().getMapper(StockEntryMapper.class);
    }

    public StockEntryMapper getMapper() {
        return mapper;
    }

}
