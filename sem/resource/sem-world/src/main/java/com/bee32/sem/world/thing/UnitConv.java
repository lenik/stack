package com.bee32.sem.world.thing;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKeyManyToMany;

import com.bee32.plover.orm.ext.dict.NameDict;

/**
 * 单位换算表
 */
@Entity
public class UnitConv
        extends NameDict {

    private static final long serialVersionUID = 1L;

    UnitConv parent;
    boolean natural;
    Unit from;
    Map<Unit, Double> ratioMap = new HashMap<Unit, Double>();

    public UnitConv() {
        super();
    }

    public UnitConv(Unit from) {
        super("nat:" + from.getName(), "自然换算表（" + from.getLabel() + "）");
        this.natural = true;
        this.from = from;
    }

    public UnitConv(String name, String label, Unit from) {
        super(name, label);
        if (from == null)
            throw new NullPointerException("from");
        this.from = from;
    }

    /**
     * 父换算表。如果本换算表中有重复的换算率定义，以本表为准。
     */
    @ManyToOne
    public UnitConv getParent() {
        return parent;
    }

    public void setParent(UnitConv parent) {
        this.parent = parent;
    }

    /**
     * 是否为自然换算表。
     */
    @Column(nullable = false)
    public boolean isNatural() {
        return natural;
    }

    public void setNatural(boolean natural) {
        this.natural = natural;
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
    @JoinTable(name = "UnitConvEntry")
    @MapKeyManyToMany
    @Column(name = "ratio", nullable = false)
    public Map<Unit, Double> getRatioMap() {
        return ratioMap;
    }

    public void setRatioMap(Map<Unit, Double> ratioMap) {
        if (ratioMap == null)
            throw new NullPointerException("ratioMap");
        this.ratioMap = ratioMap;
    }

    /**
     * Get the conversion ratio to the specified unit.
     *
     * @param toUnit
     *            Non-<code>null</code> unit to whose conversion ratio is asked.
     * @return <code>null</code> if the conversion ratio to the <code>toUnit</code> is not defined.
     */
    @Transient
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

    public static UnitConv NATURAL_METER = new UnitConv(Unit.METER);
    public static UnitConv NATURAL_SQUARE_METER = new UnitConv(Unit.SQUARE_METER);
    public static UnitConv NATURAL_CUBIC_METER = new UnitConv(Unit.CUBIC_METER);
    public static UnitConv NATURAL_GRAM = new UnitConv(Unit.GRAM);
    public static UnitConv NATURAL_NEWTON = new UnitConv(Unit.NEWTON);
    public static UnitConv NATURAL_LITER = new UnitConv(Unit.LITER);

    static {
        NATURAL_METER.setRatio(Unit.KILOMETER, 0.001);
        NATURAL_METER.setRatio(Unit.DECIMETER, 10);
        NATURAL_METER.setRatio(Unit.CENTIMETER, 100);
        NATURAL_METER.setRatio(Unit.MILLIMETER, 1000);

        NATURAL_CUBIC_METER.setRatio(Unit.LITER, 1000);

        NATURAL_SQUARE_METER.setRatio(Unit.SQUARE_CENTIMETER, 10000);
        NATURAL_SQUARE_METER.setRatio(Unit.SQUARE_KILOMETER, 0.000001);

        NATURAL_GRAM.setRatio(Unit.KILOGRAM, 0.001);
        NATURAL_GRAM.setRatio(Unit.MILLIGRAM, 1000);

        NATURAL_NEWTON.setRatio(Unit.KILOGRAM, 1.0 / 9.80);

        NATURAL_LITER.setRatio(Unit.CUBIC_METER, 0.001);
        NATURAL_LITER.setRatio(Unit.MILLILITER, 1000);
    }

}
