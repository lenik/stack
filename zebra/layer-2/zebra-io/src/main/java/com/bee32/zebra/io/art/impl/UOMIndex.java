package com.bee32.zebra.io.art.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 定义度量中使用的单位。
 * 
 * @label 度量单位
 * 
 * @rel art/: 管理物料
 */
@ObjectType(UOM.class)
public class UOMIndex
        extends FooIndex {

    public UOMIndex(IQueryable context) {
        super(context);
    }

}
