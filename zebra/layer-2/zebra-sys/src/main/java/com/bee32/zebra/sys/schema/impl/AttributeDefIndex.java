package com.bee32.zebra.sys.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.lily.model.base.schema.AttributeDef;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 对象属性的形而上定义。
 * 
 * 一个对象可以有多个属性，但同名的属性只能出现一次。
 * 
 * @label 属性定义
 */
@ObjectType(AttributeDef.class)
public class AttributeDefIndex
        extends QuickIndex {

    public AttributeDefIndex(IQueryable context) {
        super(context);
    }

}
