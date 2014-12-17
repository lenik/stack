package com.bee32.zebra.erp.stock.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 仓库中的楼层、区间、货架、单元格等按层次结构组织的空间子区域。
 * <p>
 * 库位用来管理存货的堆放端点。
 * 
 * @label 库位
 * 
 * @rel warehouse/: 管理仓库
 * @rel art/: 管理物料
 */
public class CellManager
        extends CoEntityManager {

    CellMapper mapper;

    public CellManager() {
        mapper = VhostDataService.getInstance().getMapper(CellMapper.class);
    }

    public CellMapper getMapper() {
        return mapper;
    }

}
