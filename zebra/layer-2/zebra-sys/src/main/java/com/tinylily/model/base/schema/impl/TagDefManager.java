package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooManager;
import com.tinylily.model.base.schema.TagDef;

/**
 * 标签用于对事物进行分类。
 * 
 * @label 标签定义
 */
@PathToken("tag")
public class TagDefManager
        extends FooManager {

    public TagDefManager(IQueryable context) {
        super(TagDef.class, context);
    }

}
