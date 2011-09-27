package com.bee32.sem.world.thing;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 单位换算表
 */
@Entity
public class UnitConv
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Unit unit;
    Map<Unit, Double> scaleMap = new HashMap<Unit, Double>();

    public UnitConv() {
        super();
    }

    public UnitConv(String label, Unit unit) {
        super(label);
        if (unit == null)
            throw new NullPointerException("unit");
        this.label = label;
        this.unit = unit;
    }

    /**
     * 单元单位，数量为1的一方。 如：1m -> 1.5kg，换算率=1.5，单元单位为m，换算单位为kg。
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @ElementCollection
    @JoinTable(name = "UnitConvEntry")
    @MapKeyJoinColumn(name = "stdUnit")
    @Column(name = "scale", nullable = false)
    public Map<Unit, Double> getScaleMap() {
        return scaleMap;
    }

    public void setScaleMap(Map<Unit, Double> scaleMap) {
        if (scaleMap == null)
            throw new NullPointerException("ratioMap");
        this.scaleMap = scaleMap;
    }

    /**
     * Get the conversion ratio to the specified unit.
     *
     * @param convUnit
     *            Non-<code>null</code> unit to whose conversion ratio is asked.
     * @return <code>null</code> if the conversion ratio to the <code>toUnit</code> is not defined.
     */
    @Transient
    public Double getScale(Unit convUnit) {
        if (convUnit == null)
            throw new NullPointerException("convUnit");
        Double scale = scaleMap.get(convUnit);
        return scale;
    }

    /**
     * Set the conversion ration to the specified unit.
     *
     * @param convUnit
     *            Non-<code>null</code> unit to whose conversion ratio is to be set.
     * @param scale
     *            The conversion ratio to the <code>toUnit</code>.
     */
    public void setScale(Unit convUnit, double scale) {
        if (convUnit == null)
            throw new NullPointerException("convUnit");
        scaleMap.put(convUnit, scale);
    }

}
