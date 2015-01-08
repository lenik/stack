package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.erp.fab.FabQcDef;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 质量检验参数定义。
 * 
 * @label 质检参数定义
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(FabQcDef.class)
public class FabQcDefIndex
        extends FooIndex {

    public FabQcDefIndex(IQueryable context) {
        super(context);
    }

}
