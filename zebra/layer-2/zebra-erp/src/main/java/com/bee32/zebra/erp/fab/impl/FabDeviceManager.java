package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.erp.fab.FabDevice;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 生产工艺中需要用到的机器设备。
 * 
 * @label 工艺设备
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(FabDevice.class)
public class FabDeviceManager
        extends FooManager {

    public FabDeviceManager(IQueryable context) {
        super(context);
    }

}
