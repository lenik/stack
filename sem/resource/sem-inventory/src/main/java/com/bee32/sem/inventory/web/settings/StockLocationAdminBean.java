package com.bee32.sem.inventory.web.settings;

import java.util.List;

import javax.inject.Inject;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.web.ChooseStockLocationDialogBean;
import com.bee32.sem.inventory.web.business.StockDictsBean;
import com.bee32.sem.misc.TreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.world.thing.UnitDto;

@ForEntity(StockLocation.class)
public class StockLocationAdminBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    protected StockDictsBean dicts;

    List<StockWarehouseDto> warehouses;
    List<UnitDto> units;

    int selectedWarehouseId = -1;

    public StockLocationAdminBean() {
        super(StockLocation.class, StockLocationDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        elements.add(new Equals("warehouse.id", selectedWarehouseId));
    }

    @Override
    protected boolean postValidate(List<?> dtos)
            throws Exception {
        if (selectedWarehouseId == -1) {
            uiLogger.warn("请先选择需要添加库位的${tr.inventory.warehouse}");
            return false;
        }
        StockLocationDto location = getOpenedObject();
        location.setWarehouse(getSelectedWarehouse());
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        BEAN(ChooseStockLocationDialogBean.class).refreshTree();
    }

    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
            refreshTree();
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

}
