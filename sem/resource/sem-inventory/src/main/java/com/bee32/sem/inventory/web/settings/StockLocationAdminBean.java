package com.bee32.sem.inventory.web.settings;

import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitDto;

@ForEntity(StockLocation.class)
public class StockLocationAdminBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<StockWarehouseDto> warehouses;
    List<UnitDto> units;

    StockWarehouseDto selectedWarehouse;

    public StockLocationAdminBean() {
        super(StockLocation.class, StockLocationDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        Integer warehouseId = null;
        if (selectedWarehouse != null)
            warehouseId = selectedWarehouse.getId();
        if (warehouseId == null)
            warehouseId = -1;
        elements.add(new Equals("warehouse.id", warehouseId));
    }

    @Override
    protected void save(int saveFlags, String hint) {
        if (selectedWarehouse == null) {
            uiLogger.warn("请先选择需要添加库位的仓库");
            return;
        }
        StockLocationDto location = getActiveObject();
        location.setWarehouse(selectedWarehouse);
        if (location.getCapacityUnit().getId().isEmpty()) {
            UnitDto nullUnitDto = new UnitDto().ref();
            location.setCapacityUnit(nullUnitDto);
        }
        // continue save.
        super.save(saveFlags, hint);
    }

    public synchronized List<StockWarehouseDto> getWarehouses() {
        if (warehouses == null) {
            List<StockWarehouse> _warehouses = serviceFor(StockWarehouse.class).list();
            warehouses = DTOs.mrefList(StockWarehouseDto.class, _warehouses);
        }
        return warehouses;
    }

    public synchronized List<UnitDto> getUnits() {
        if (units == null) {
            List<Unit> _units = serviceFor(Unit.class).list();
            units = DTOs.mrefList(UnitDto.class, _units);
        }
        return units;
    }

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

}
