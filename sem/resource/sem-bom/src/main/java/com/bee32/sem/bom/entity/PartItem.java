package com.bee32.sem.bom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.entity.Material;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "part_material_seq", allocationSize = 1)
public class PartItem
        extends UIEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Part parent;

    Part part;
    Material material;

    BigDecimal quantity = new BigDecimal(1);

    boolean valid = true;
    Date validDateFrom;
    Date validDateTo;

    /**
     * 所属的部件
     */
    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Part getParent() {
        return parent;
    }

    public void setParent(Part parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    /**
     * 作为半成品的子部件。在这种情况下，忽略作为原材料的属性。
     */
    @NaturalId(mutable = true)
    @ManyToOne
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    /**
     * 作为原材料的子部件。
     *
     * 当子部件为半成品时，忽略此属性。
     */
    @NaturalId(mutable = true)
    @ManyToOne
    public Material getMaterial() {
        if (part != null)
            return null;
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 数量
     */
    @Column(precision = QTY_ITEM_PRECISION, scale = QTY_ITEM_SCALE, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    public void setQuantity(double quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    /**
     * 是否有效
     */
    @Column(nullable = false)
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * 起用日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Date validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    /**
     * 无效日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Date validDateTo) {
        this.validDateTo = validDateTo;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(part), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (parent == null)
            throw new NullPointerException("parent");
        if (part == null && material == null)
            throw new NullPointerException("part | material");

        return selectors( //
                selector(prefix + "parent", parent), //
                selector(prefix + "part", part, true), //
                selector(prefix + "material", material, true));
    }

    @Transient
    public Classification getClassification() {
        if (part != null)
            return Classification.SEMI;
        else
            return Classification.RAW;
    }

}
