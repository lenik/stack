package com.bee32.sem.world.thing;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.xp.EntityExt;
import com.bee32.plover.ox1.xp.XPool;
import com.bee32.sem.world.color.NaturalColor;

/**
 * 物品
 *
 * 一般物品。
 */
@ModelTemplate
// High-Level model template.
@MappedSuperclass
@Green
public abstract class Thing<X extends XPool<?>>
        extends EntityExt<Long, X> {

    private static final long serialVersionUID = 1L;

    public static final int SERIAL_LENGTH = 32;
    public static final int UNIT_HINT_LENGTH = Unit.HINT_LENGTH;
    public static final int BARCODE_LENGTH = 30;
    public static final int MODELSPEC_LENGTH = 200;

    String serial;
    String barCode;
    Unit unit = predefined(Units.class).PIECE;
    String unitHint;
    UnitConv unitConv;
    String modelSpec;
    NaturalColor color = new NaturalColor();

    public Thing() {
        super();
    }

    public Thing(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof Thing)
            _populate((Thing<X>) source);
        else
            super.populate(source);
    }

    protected void _populate(Thing<X> o) {
        super._populate(o);
        serial = o.serial;
        barCode = o.barCode;
        unit = o.unit;
        unitHint = o.unitHint;
        unitConv = o.unitConv;
        modelSpec = o.modelSpec;
        color = o.color; // clone?
    }

    @Override
    protected void populateKeywords(Collection<String> keywords) {
        super.populateKeywords(keywords);
        // keywords.add(modelSpec);
    }

    /**
     * 名称
     */
    @Transient
    public String getDisplayName() {
        return getLabel();
    }

    /**
     * 编码
     *
     * 物品编码、物品序列号等。
     */
    @NaturalId(mutable = true)
    @Column(length = SERIAL_LENGTH, unique = true)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * 条形码
     *
     * 打印在小张白纸上的图形条码的数字。
     */
    @Column(length = BARCODE_LENGTH)
    @Index(name = "##_bar_code")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 物品单位
     *
     * 物品的衡量单位。
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Unit getUnit() {
        return unit;
    }

    /**
     * 物品单位
     *
     * 物品的衡量单位。
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * 单位提示
     *
     * 说明单位的应用场合。如“容量”、“体积“。
     */
    @Column(name = "unitHint", length = UNIT_HINT_LENGTH)
    String get_unitHint() { // default -> null.
        if (unitHint != null)
            if (unitHint.equals(unit.getHint()))
                unitHint = null;
        return unitHint;
    }

    void set_unitHint(String unitHint) {
        this.unitHint = unitHint;
    }

    @Transient
    public String getUnitHint() { // null -> default.
        if (unitHint == null)
            return getUnit().getHint();
        else
            return unitHint;
    }

    public void setUnitHint(String unitHint) {
        if (unitHint == null)
            throw new NullPointerException("unitHint");
        this.unitHint = unitHint;
    }

    /**
     * 单位换算表
     *
     * 物品采用的单位换算表。
     */
    @ManyToOne
    public UnitConv getUnitConv() {
        return unitConv;
    }

    public void setUnitConv(UnitConv unitConv) {
        this.unitConv = unitConv;
    }

    /**
     * 假如主单位为 米，1米对应 3公斤，则 addUnitConv(KG, 3.0)
     */
    public void addUnitConv(Unit convUnit, double scale) {
        if (unitConv == null) {
            if (label == null)
                throw new IllegalStateException("label isn't initialized");
            if (unit == null)
                throw new IllegalStateException("unit isn't initialized");
            unitConv = new UnitConv(getLabel(), unit);
        }
        unitConv.setScale(convUnit, scale);
    }

    /**
     * 规格型号
     *
     * 提示物品主要特性的关键规格型号。
     */
    @Column(length = MODELSPEC_LENGTH)
    @Index(name = "##_model_spec")
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    /**
     * 颜色
     *
     * 物品的外观色彩。
     */
    @Embedded
    public NaturalColor getColor() {
        return color;
    }

    public void setColor(NaturalColor color) {
        if (color == null)
            throw new NullPointerException("color");
        this.color = color;
    }

}
