package com.bee32.sem.world.thing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;

public class UnitConvDto
        extends UIEntityDto<UnitConv, Long> {

    private static final long serialVersionUID = 1L;

    public static final int MAP = 1;

    UnitDto unit;
    Map<UnitDto, Double> scaleMap;

    // scaleMap的一个不同形式副本,便于在办面中以el存取
    List<ScaleItem> itemList = new ArrayList<ScaleItem>();

    public UnitConvDto() {
        super();
    }

    public UnitConvDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(UnitConv source) {
        unit = mref(UnitDto.class, source.getUnit());

        if (selection.contains(MAP)) {
            scaleMap = new HashMap<UnitDto, Double>();
            for (Entry<Unit, Double> entry : source.getScaleMap().entrySet()) {
                UnitDto scaleUnit = mref(UnitDto.class, entry.getKey());
                double scale = entry.getValue();
                scaleMap.put(scaleUnit, scale);
            }

            for (Entry<Unit, Double> entry : source.getScaleMap().entrySet()) {
                UnitDto scaleUnit = mref(UnitDto.class, entry.getKey());
                double scale = entry.getValue();
                ScaleItem item = new ScaleItem();
                item.setUnit(scaleUnit);
                item.setScale(scale);
                itemList.add(item);
            }
        }

    }

    @Override
    protected void _unmarshalTo(UnitConv target) {
        merge(target, "unit", unit);

        if (selection.contains(MAP)) {
            if (itemList != null && itemList.size() > 0) {
                // 如果有itemList,则itemList优先
                Map<Unit, Double> _map = new HashMap<Unit, Double>();
                for (ScaleItem item : itemList) {
                    Unit _unit = item.getUnit().unmarshal();
                    double scale = item.getScale();
                    _map.put(_unit, scale);
                }
                target.setScaleMap(_map);
            } else {
                Map<Unit, Double> _scaleMap = new HashMap<Unit, Double>();
                for (Entry<UnitDto, Double> entry : scaleMap.entrySet()) {
                    Unit _unit = entry.getKey().unmarshal(getSession());
                    double scale = entry.getValue();
                    _scaleMap.put(_unit, scale);
                }
                target.setScaleMap(_scaleMap);
            }
        }

    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        unit = new UnitDto().ref(map.getString("unit"));

        if (selection.contains(MAP)) {
            throw new NotImplementedException();
        }
    }

    public void addScaleItem(ScaleItem item) {
        this.itemList.add(item);
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        if (unit == null)
            throw new NullPointerException("unit");
        this.unit = unit;
    }

    public List<ScaleItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ScaleItem> itemList) {
        this.itemList = itemList;
    }

    public Map<UnitDto, Double> getScaleMap() {
        return scaleMap;
    }

    public void setScaleMap(Map<UnitDto, Double> scaleMap) {
        if (scaleMap == null)
            throw new NullPointerException("scaleMap");
        this.scaleMap = scaleMap;
    }

    public Double getScale(UnitDto unit) {
        return getScale(unit.getId());
    }

    public Double getScale(String unitId) {
        for (Entry<UnitDto, Double> entry : scaleMap.entrySet()) {
            if (entry.getKey().getId() == unitId)
                return entry.getValue();
        }
        return null;
    }

}
