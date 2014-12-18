package com.bee32.zebra.erp.stock.impl;

import com.bee32.zebra.erp.stock.Place;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 仓库、楼层、区间、货架、单元格等按层次结构组织的空间区域。
 * <p>
 * 区域可以用来堆放存货。
 * 
 * @label 区域/位置
 * 
 * @rel art/: 管理物料
 */
public class PlaceManager
        extends FooManager {

    PlaceMapper mapper;

    public PlaceManager() {
        super(Place.class);
        mapper = VhostDataService.getInstance().getMapper(PlaceMapper.class);
    }

    public PlaceMapper getMapper() {
        return mapper;
    }

}
