package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.erp.fab.FabStep;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 
 * @label 工序单
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(FabStep.class)
public class FabStepManager
        extends FooManager {

    public FabStepManager(IQueryable context) {
        super(context);
    }

}
