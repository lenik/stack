package com.bee32.zebra.erp.stock.impl;

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
public class CellManager
        extends CoEntityManager {

    CellMapper mapper;

    public CellManager() {
        mapper = VhostDataService.getInstance().getMapper(CellMapper.class);
    }

    public CellMapper getMapper() {
        return mapper;
    }

}
