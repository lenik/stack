package com.bee32.zebra.erp.fab.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.erp.fab.FabQcDef;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 质量检验参数定义。
 * 
 * @label 质检参数定义
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@PathToken("qc")
public class FabQcDefManager
        extends FooManager {

    FabQcDefMapper mapper;

    public FabQcDefManager() {
        super(FabQcDef.class);
        mapper = VhostDataService.getInstance().getMapper(FabQcDefMapper.class);
    }

    public FabQcDefMapper getMapper() {
        return mapper;
    }

}
