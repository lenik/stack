package com.tinylily.model.base.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.tinylily.model.base.schema.PriorityDef;

/**
 * 定义优先次序。
 * 
 * @label 优先级
 */
@ObjectType(PriorityDef.class)
public class PriorityDefIndex
        extends QuickIndex {

    public PriorityDefIndex(IQueryable context) {
        super(context);
    }

}
