package com.bee32.zebra.erp.stock.impl;

import com.bee32.zebra.erp.stock.UOM;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 定义度量中使用的单位。
 * 
 * @label 度量单位
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class UOMManager
        extends FooManager {

    UOMMapper mapper;

    public UOMManager() {
        super(UOM.class);
        mapper = VhostDataService.getInstance().getMapper(UOMMapper.class);
    }

    public UOMMapper getMapper() {
        return mapper;
    }

}
