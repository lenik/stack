package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.erp.fab.FabOpCode;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 标准化生产过程（步骤）中具体的操作行为。
 * 
 * @label 操作代码
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(FabOpCode.class)
public class FabOpCodeManager
        extends FooManager {

    public FabOpCodeManager(IQueryable context) {
        super(context);
    }

}
