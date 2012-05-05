package com.bee32.sem.world.thing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.site.scope.PerSite;
import com.bee32.sem.sandbox.UIHelper;

@PerSite
public class ThingDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<UnitDto> units;

    public SelectableList<UnitDto> getUnits() {
        if (units == null) {
            synchronized (this) {
                if (units == null) {
                    units = mrefList(Unit.class, UnitDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(units);
    }

    List<SelectItem> unitSelectItems;

    public List<SelectItem> getUnitSelectItems() {
        if (unitSelectItems == null) {
            synchronized (this) {
                if (unitSelectItems == null) {
                    unitSelectItems = new ArrayList<SelectItem>();
                    for (UnitDto unit : getUnits()) {
                        SelectItem item = new SelectItem();
                        item.setValue(unit.getId());
                        item.setLabel(unit.getLabel());
                        unitSelectItems.add(item);
                    }
                }
            }
        }
        return unitSelectItems;
    }

    @Deprecated
    public List<SelectItem> getStandardUnits() {
        List<Unit> stdUnitList = ctx.data.access(Unit.class).list(UnitCriteria.standardUnits);
        List<UnitDto> stdUnitDtoList = DTOs.marshalList(UnitDto.class, stdUnitList);
        return UIHelper.selectItemsFromDict(stdUnitDtoList);
    }

    @Deprecated
    public List<SelectItem> getAllUnits() {
        List<Unit> unitList = ctx.data.access(Unit.class).list();
        List<UnitDto> unitDtoList = DTOs.marshalList(UnitDto.class, unitList);
        return UIHelper.selectItemsFromDict(unitDtoList);
    }

    public synchronized void invalidateUnits() {
        units = null;
        unitSelectItems = null;
    }

}
