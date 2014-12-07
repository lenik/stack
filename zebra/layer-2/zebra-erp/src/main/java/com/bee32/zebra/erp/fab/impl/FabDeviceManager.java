package com.bee32.zebra.erp.fab.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FabDeviceManager
        extends CoEntityManager {

    FabDeviceMapper mapper;

    public FabDeviceManager() {
        mapper = VhostDataService.getInstance().getMapper(FabDeviceMapper.class);
    }

    public FabDeviceMapper getMapper() {
        return mapper;
    }

}
