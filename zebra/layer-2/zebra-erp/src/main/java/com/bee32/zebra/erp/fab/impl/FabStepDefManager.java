package com.bee32.zebra.erp.fab.impl;

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 定义生产工艺流程中的一个特定步骤。
 * 
 * @label 工艺步骤
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FabStepDefManager
        extends FooManager {

    FabStepDefMapper mapper;

    public FabStepDefManager() {
        super(FabStepDef.class);
        mapper = VhostDataService.getInstance().getMapper(FabStepDefMapper.class);
    }

    public FabStepDefMapper getMapper() {
        return mapper;
    }

}
