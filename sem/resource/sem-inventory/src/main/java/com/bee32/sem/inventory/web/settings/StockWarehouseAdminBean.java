package com.bee32.sem.inventory.web.settings;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.util.StockWarehouseCriteria;
import com.bee32.sem.material.dto.StockWarehouseDto;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(StockWarehouse.class)
public class StockWarehouseAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public StockWarehouseAdminBean() {
        super(StockWarehouse.class, StockWarehouseDto.class, 0);
    }

    public void addAddressRestriction() {
        setSearchFragment("address", "限定地址 " + searchPattern,//
                StockWarehouseCriteria.addressLike(searchPattern));
        searchPattern = null;
    }

    public void addPhoneRestriction() {
        setSearchFragment("phone", "限定电话 " + searchPattern,//
                StockWarehouseCriteria.phoneLike(searchPattern));
        searchPattern = null;
    }

}
