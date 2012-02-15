package com.bee32.sem.inventory.web.settings;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.web.ChooseStockLocationDialogBean;
import com.bee32.sem.inventory.web.business.StockDictsBean;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitDto;

@ForEntity(StockLocation.class)
public class StockLocationAdminBean
        extends SimpleTreeEntityViewBean {

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

    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap) throws Exception {
        super.postUpdate(uMap);
        ctx.bean.getBean(ChooseStockLocationDialogBean.class).refreshTree();
    }

    @Override
    protected void save(int saveFlags, String hint) {
        if (selectedWarehouseId == -1) {
            uiLogger.warn("请先选择需要添加库位的仓库");
            return;
        }
        StockLocationDto location = getOpenedObject();
        location.setWarehouse(getSelectedWarehouse());
        if (StringUtils.isEmpty(location.getCapacityUnit().getId())) {
            UnitDto nullUnitDto = new UnitDto().ref();
            location.setCapacityUnit(nullUnitDto);
        }
        // continue save.
        super.save(saveFlags, hint);
    }


    public synchronized List<UnitDto> getUnits() {
        if (units == null) {
            List<Unit> _units = ctx.data.access(Unit.class).list();
            units = DTOs.mrefList(UnitDto.class, _units);
        }
        return units;
    }
}
