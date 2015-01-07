package com.tinylily.model.base.schema.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooManager;
import com.tinylily.model.base.schema.PriorityDef;

/**
 * 定义优先次序。
 * 
 * @label 优先级
 */
public class PriorityDefManager
        extends FooManager {

    public PriorityDefManager(IQueryable context) {
        super(PriorityDef.class, context);
    }

}
