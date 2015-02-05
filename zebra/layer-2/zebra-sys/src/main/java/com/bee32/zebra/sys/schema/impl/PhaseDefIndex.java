package com.bee32.zebra.sys.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.tinylily.model.base.schema.PhaseDef;

/**
 * 用于定义事件进展到什么程度。
 * 
 * @label 阶段
 */
@ObjectType(PhaseDef.class)
public class PhaseDefIndex
        extends QuickIndex {

    public PhaseDefIndex(IQueryable context) {
        super(context);
    }

}
