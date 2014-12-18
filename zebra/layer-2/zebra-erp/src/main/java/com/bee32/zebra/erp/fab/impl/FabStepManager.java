package com.bee32.zebra.erp.fab.impl;

import com.bee32.zebra.erp.fab.FabStep;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 
 * @label 工序单
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FabStepManager
        extends FooManager {

    FabStepMapper mapper;

    public FabStepManager() {
        super(FabStep.class);
        mapper = VhostDataService.getInstance().getMapper(FabStepMapper.class);
    }

    public FabStepMapper getMapper() {
        return mapper;
    }

}
