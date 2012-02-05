package com.bee32.sem.inventory.web.settings;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(StockWarehouse.class)
public class StockWarehouseAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public StockWarehouseAdminBean() {
        super(StockWarehouse.class, StockWarehouseDto.class, 0);
    }

}
