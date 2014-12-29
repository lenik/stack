package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.tk.sea.FooManager;

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

    public FabStepDefManager(IQueryable context) {
        super(FabStepDef.class, context);
    }

}
