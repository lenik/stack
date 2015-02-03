package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 定义生产工艺流程中的一个特定步骤。
 * 
 * @label 工艺步骤
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(FabStepDef.class)
public class FabStepDefIndex
        extends QuickIndex {

    public FabStepDefIndex(IQueryable context) {
        super(context);
    }

}
