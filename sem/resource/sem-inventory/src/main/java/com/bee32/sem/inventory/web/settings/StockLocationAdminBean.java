package com.bee32.sem.inventory.web.settings;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockWarehouse;

@Component
@Scope("view")
public class StockLocationAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private StockWarehouseDto stockWarehouse;

    public StockWarehouseDto getStockWarehouse() {
        if(stockWarehouse == null) {
            stockWarehouse = new StockWarehouseDto().create();
        }
        return stockWarehouse;
    }

    public void setStockWarehouse(StockWarehouseDto stockWarehouse) {
        this.stockWarehouse = stockWarehouse;
    }




    public List<SelectItem> getStockWarehouses() {
        List<StockWarehouse> stockWarehouses = serviceFor(StockWarehouse.class).list();
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.marshalList(
                StockWarehouseDto.class, stockWarehouses);

        List<SelectItem> items = new ArrayList<SelectItem>();

        for (StockWarehouseDto stockWarehouseDto : stockWarehouseDtos) {
            SelectItem item = new SelectItem(stockWarehouseDto.getId(), stockWarehouseDto.getName());
            items.add(item);
        }

        return items;
    }



    public void onSwChange(ActionEvent e) {
        Object o = e.getSource();
    }

}
