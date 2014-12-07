package com.bee32.zebra.erp.order.impl;

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
public class FabOrderManager
        extends CoEntityManager {

    FabOrderMapper mapper;

    public FabOrderManager() {
        mapper = VhostDataService.getInstance().getMapper(FabOrderMapper.class);
    }

    public FabOrderMapper getMapper() {
        return mapper;
    }

}
