package com.bee32.sem.world.thing;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;

import org.hibernate.annotations.CollectionOfElements;

import com.bee32.plover.orm.ext.dict.NameDict;

/**
 * 单位换算表
 */
@Entity
public class UnitConv
        extends NameDict {

    private static final long serialVersionUID = 1L;

    UnitConv parent;
    Unit from;
    Map<Unit, Double> ratioMap = new HashMap<Unit, Double>();

    public UnitConv() {
        super();
    }

    public UnitConv(String name, String label, Unit from) {
        super(name, label);

        if (from == null)
            throw new NullPointerException("from");
        this.from = from;
    }

    @ManyToOne
    public UnitConv getParent() {
        return parent;
    }

    public void setParent(UnitConv parent) {
        this.parent = parent;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Unit getFrom() {
        return from;
    }

    public void setFrom(Unit from) {
        this.from = from;
    }

    @CollectionOfElements
    @JoinTable(name = "UnitRatioMap")
    @MapKey(name = "unit")
    @Column(name = "ratio", nullable = false, precision = 12, scale = 5)
    public Map<Unit, Double> getRatioMap() {
        return ratioMap;
    }

    public void setRatioMap(Map<Unit, Double> ratioMap) {
        if (ratioMap == null)
            throw new NullPointerException("ratioMap");
        this.ratioMap = ratioMap;
    }

    /**
     * Get the conversion ration to the specified unit.
     *
     * @param toUnit
     *            Non-<code>null</code> unit to whose conversion ratio is asked.
     * @return <code>null</code> if the conversion ratio to the <code>toUnit</code> is not defined.
     */
    public Double getRatio(Unit toUnit) {
        if (toUnit == null)
            throw new NullPointerException("toUnit");

        Double ratio = ratioMap.get(toUnit);

        // Find in parent unit conv also.
        if (ratio == null && parent != null)
            ratio = parent.getRatio(toUnit);

        return ratio;
    }

    /**
     * Set the conversion ration to the specified unit.
     *
     * @param toUnit
     *            Non-<code>null</code> unit to whose conversion ratio is to be set.
     * @param ratio
     *            The conversion ratio to the <code>toUnit</code>.
     */
    public void setRatio(Unit toUnit, double ratio) {
        if (toUnit == null)
            throw new NullPointerException("toUnit");
        ratioMap.put(toUnit, ratio);
    }

}
