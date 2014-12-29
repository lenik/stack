package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooManager;
import com.tinylily.model.base.schema.AttributeDef;

/**
 * 对象属性的形而上定义。
 * 
 * 一个对象可以有多个属性，但同名的属性只能出现一次。
 * 
 * @label 属性定义
 */
@PathToken("att")
public class AttributeDefManager
        extends FooManager {

    public AttributeDefManager(IQueryable context) {
        super(AttributeDef.class, context);
    }

}
