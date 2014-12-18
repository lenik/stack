package com.bee32.zebra.erp.fab.impl;

import com.bee32.zebra.erp.fab.FabDevice;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 生产工艺中需要用到的机器设备。
 * 
 * @label 工艺设备
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FabDeviceManager
        extends FooManager {

    FabDeviceMapper mapper;

    public FabDeviceManager() {
        super(FabDevice.class);
        mapper = VhostDataService.getInstance().getMapper(FabDeviceMapper.class);
    }

    public FabDeviceMapper getMapper() {
        return mapper;
    }

}
