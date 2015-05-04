package com.bee32.zebra.sys.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.lily.model.base.schema.CategoryDef;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 用于对象分类。
 * <p>
 * 和标签不同，一个对象只能属于一个类别。
 * 
 * @label 类别
 */
@ObjectType(CategoryDef.class)
public class CategoryDefIndex
        extends QuickIndex {

    public CategoryDefIndex(IQueryable context) {
        super(context);
    }

}
