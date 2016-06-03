package com.bee32.zebra.sys.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.lily.model.base.schema.ParameterDef;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 对象参数的形而上定义。
 * 
 * 一个对象可以有多个参数，但同名的参数只能出现一次。
 * 
 * @label 参数定义
 */
@ObjectType(ParameterDef.class)
public class ParameterDefIndex
        extends QuickIndex<ParameterDef> {

}
