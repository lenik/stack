package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooManager;
import com.tinylily.model.base.schema.PhaseDef;

/**
 * 用于定义事件进展到什么程度。
 * 
 * @label 阶段
 */
@PathToken("phase")
public class PhaseDefManager
        extends FooManager {

    public PhaseDefManager(IQueryable context) {
        super(PhaseDef.class, context);
    }

}
