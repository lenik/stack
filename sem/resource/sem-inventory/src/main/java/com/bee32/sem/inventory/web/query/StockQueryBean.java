package com.bee32.sem.inventory.web.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;

@Component
@Scope("view")
public class StockQueryBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private StockWarehouseDto selectedWarehouse;

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

    public List<StockOrderItemDto> getItems() {
        return new ArrayList<StockOrderItemDto>();
    }

}
