package com.bee32.zebra.io.art.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 定义度量中使用的单位。
 * 
 * @label 度量单位
 * 
 * @rel art/: 管理物料
 */
public class UOMManager
        extends FooManager {

    public UOMManager(IQueryable context) {
        super(UOM.class, context);
    }

}
