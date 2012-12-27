package com.bee32.sem.material.entity;

import static com.bee32.plover.ox1.config.DecimalConfig.QTY_ITEM_PRECISION;
import static com.bee32.plover.ox1.config.DecimalConfig.QTY_ITEM_SCALE;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * 物料仓库选项
 *
 * 物料对应各个仓库的选项。
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_warehouse_option_seq", allocationSize = 1)
public class MaterialWarehouseOption
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockWarehouse warehouse; // null for "default" options.
    BigDecimal safetyStock = new BigDecimal(1);
    int stkPeriod = 365;

    @Override
    public void populate(Object source) {
        if (source instanceof MaterialWarehouseOption)
            _populate((MaterialWarehouseOption) source);
        else
            super.populate(source);
    }

    protected void _populate(MaterialWarehouseOption o) {
        super._populate(o);
        material = o.material;
        warehouse = o.warehouse;
        safetyStock = o.safetyStock;
        stkPeriod = o.stkPeriod;
    }

    /**
     * 物料
     *
     * 相关的物料。
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    /**
     * 仓库
     *
     * 选项针对的具体仓库。
     */
    @NaturalId
    @ManyToOne(optional = false)
    public StockWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouse warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        this.warehouse = warehouse;
    }

    /**
     * 安全库存
     *
     * 该仓库中的安全库存数量。默认为1。
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(BigDecimal safetyStock) {
        this.safetyStock = safetyStock;
    }

    /**
     * 循环盘点周期
     *
     * 单位为天数， 默认为1年。
     */
    @Column(nullable = false)
    public int getStkPeriod() {
        return stkPeriod;
    }

    public void setStkPeriod(int stkPeriod) {
        this.stkPeriod = stkPeriod;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(material), //
                naturalIdOpt(warehouse));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "material", material), //
                selector(prefix + "warehouse", warehouse));
    }

    @Override
    protected CEntity<?> owningEntity() {
        return material;
    }

}
