package com.tinylily.model.base.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooIndex;
import com.tinylily.model.base.schema.AttributeDef;

/**
 * 对象属性的形而上定义。
 * 
 * 一个对象可以有多个属性，但同名的属性只能出现一次。
 * 
 * @label 属性定义
 */
@ObjectType(AttributeDef.class)
public class AttributeDefIndex
        extends FooIndex {

    public AttributeDefIndex(IQueryable context) {
        super(context);
    }

}
