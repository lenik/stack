package com.bee32.zebra.erp.stock.impl;

import com.bee32.zebra.tk.foo.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 用于堆放存货。可能是一个建筑物、楼层，通常有一个较大的空间，其中又可划分为多个库位。 多个仓库之间不可以互相重叠。
 * 
 * @label 仓库
 * 
 * @rel cell/: 管理库位
 * @rel art/: 管理产品/物料
 * 
 * @see <a href="http://info.10000link.com/newsdetail.aspx?doc=2011052090048">仓库安全规章制度</a>
 * @see <a href="http://wiki.mbalib.com/wiki/仓储安全作业管理">仓储安全作业管理</a>
 * @see <a href="http://wenku.baidu.com/view/507c66040740be1e650e9a86">仓库优化设计案例分析</a>
 */
public class WarehouseManager
        extends FooManager {

    WarehouseMapper mapper;

    public WarehouseManager() {
        mapper = VhostDataService.getInstance().getMapper(WarehouseMapper.class);
    }

    public WarehouseMapper getMapper() {
        return mapper;
    }

}
