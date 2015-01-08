package com.tinylily.model.base.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooIndex;
import com.tinylily.model.base.schema.PhaseDef;

/**
 * 用于定义事件进展到什么程度。
 * 
 * @label 阶段
 */
@ObjectType(PhaseDef.class)
public class PhaseDefIndex
        extends FooIndex {

    public PhaseDefIndex(IQueryable context) {
        super(context);
    }

}
