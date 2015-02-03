package com.tinylily.model.base.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.tinylily.model.base.schema.TagDef;

/**
 * 标签用于对事物进行分类。
 * 
 * @label 标签定义
 */
@ObjectType(TagDef.class)
public class TagDefIndex
        extends QuickIndex {

    public TagDefIndex(IQueryable context) {
        super(context);
    }

}
