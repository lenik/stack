package com.bee32.zebra.sys.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.lily.model.base.schema.TagGroupDef;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 用于区分不同系统中使用的标签集。
 * 
 * 同一个标签在不同的标签系统中可能有不同的含义，比如”黄色“在实物系统中用于对色彩分类，而在消息系统中可能代表某种警告。
 * 
 * @label 标签集
 */
@ObjectType(TagGroupDef.class)
public class TagGroupDefIndex
        extends QuickIndex<TagGroupDef> {

}
