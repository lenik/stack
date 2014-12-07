package com.bee32.zebra.erp.fab.impl;

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
public class FabStepManager
        extends CoEntityManager {

    FabStepMapper mapper;

    public FabStepManager() {
        mapper = VhostDataService.getInstance().getMapper(FabStepMapper.class);
    }

    public FabStepMapper getMapper() {
        return mapper;
    }

}
