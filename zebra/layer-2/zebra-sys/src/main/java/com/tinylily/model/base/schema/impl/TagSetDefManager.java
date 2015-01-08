package com.tinylily.model.base.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooManager;
import com.tinylily.model.base.schema.TagSetDef;

/**
 * 用于区分不同系统中使用的标签集。
 * 
 * 同一个标签在不同的标签系统中可能有不同的含义，比如”黄色“在实物系统中用于对色彩分类，而在消息系统中可能代表某种警告。
 * 
 * @label 标签集
 */
@ObjectType(TagSetDef.class)
public class TagSetDefManager
        extends FooManager {

    public TagSetDefManager(IQueryable context) {
        super(context);
    }

}
