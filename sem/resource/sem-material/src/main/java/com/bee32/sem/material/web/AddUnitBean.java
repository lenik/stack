package com.bee32.sem.material.web;

import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.ThingDictsBean;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitCriteria;
import com.bee32.sem.world.thing.UnitDto;

@ForEntity(Unit.class)
public class AddUnitBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    UnitDto unit = new UnitDto().create();

    public List<SelectItem> getStandardUnits() {
        List<Unit> unitList = DATA(Unit.class).list(UnitCriteria.standardUnits);
        List<UnitDto> unitDtoList = DTOs.marshalList(UnitDto.class, unitList);
        return UIHelper.selectItemsFromDict(unitDtoList);
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        this.unit = unit;
    }

    public void newUnit() {
        unit = new UnitDto().create();
    }

    public void addUnit() {
        try {
            Unit _unit = unit.unmarshal();
            DATA(Unit.class).saveOrUpdate(_unit);
            uiLogger.info("保存成功!");

            // Refresh units.
            BEAN(ThingDictsBean.class).invalidateUnits();
        } catch (Exception e) {
            uiLogger.error("添加单位失败", e);
        }
    }

}
