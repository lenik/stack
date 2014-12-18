package com.bee32.zebra.erp.fab.impl;

import com.bee32.zebra.erp.fab.FabOpCode;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 标准化生产过程（步骤）中具体的操作行为。
 * 
 * @label 操作代码
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FabOpCodeManager
        extends FooManager {

    FabOpCodeMapper mapper;

    public FabOpCodeManager() {
        super(FabOpCode.class);
        mapper = VhostDataService.getInstance().getMapper(FabOpCodeMapper.class);
    }

    public FabOpCodeMapper getMapper() {
        return mapper;
    }

}
