package com.bee32.sem.makebiz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DefaultValue;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 订单明细项目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_item_seq", allocationSize = 1)
public class MakeOrderItem
        extends AbstractItem
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int EXT_PROD_NAME_LENGTH = 100;
    public static final int EXT_SPEC_LENGTH = 200;
    public static final int EXT_UNIT_LENGTH = 20;

    MakeOrder parent;
    Material material;
    Date deadline;

    String externalProductName;
    String externalModelSpec;
    String externalUnit;

    boolean nameplate = true;

    @Override
    public void populate(Object source) {
        if (source instanceof MakeOrderItem)
            _populate((MakeOrderItem) source);
        else
            super.populate(source);
    }

    protected void _populate(MakeOrderItem o) {
        super._populate(o);
        parent = o.parent;
        material = o.material;
        deadline = o.deadline;
        externalProductName = o.externalProductName;
        externalModelSpec = o.externalModelSpec;
        externalUnit = o.externalUnit;
        nameplate = o.nameplate;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public MakeOrder getParent() {
        return parent;
    }

    public void setParent(MakeOrder parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @NaturalId(mutable = true)
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * 产品外部名称
     * <p>
     * 和某个客户对应，具体对应客户在MakeOrder中
     * </p>
     * <p>
     * 某个产品对于不同客户的不同叫法，对内为同一种产品(同一个物料)
     * </p>
     *
     * @return
     */
    @Column(length = EXT_PROD_NAME_LENGTH)
    public String getExternalProductName() {
        return externalProductName;
    }

    public void setExternalProductName(String externalProductName) {
        this.externalProductName = externalProductName;
    }

    /**
     * 产品的外部技术参数要求
     * <p>
     * 和某个客户对应，具体对应客户在MakeOrder中
     * </p>
     * <p>
     * 不同的客户对某个产品有不同的技术要求，但对内为同一个产品，所以技术要求相同
     * </p>
     *
     * @return
     */
    @Column(length = EXT_SPEC_LENGTH)
    public String getExternalModelSpec() {
        return externalModelSpec;
    }

    public void setExternalModelSpec(String externalModelSpec) {
        this.externalModelSpec = externalModelSpec;
    }

    @Column(length = EXT_UNIT_LENGTH)
    public String getExternalUnit() {
        return externalUnit;
    }

    public void setExternalUnit(String externalUnit) {
        this.externalUnit = externalUnit;
    }

    /**
     * @return true-使用物料名称，规格，参数; false-使用外部物料名称，规格，参数
     */
    @DefaultValue("true")
    @Column(nullable = false)
    public boolean isNameplate() {
        return nameplate;
    }

    public void setNameplate(boolean nameplate) {
        this.nameplate = nameplate;
    }

    @Transient
    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "parent", parent), //
                selector(prefix + "material", material));
    }

    @Override
    public MakeOrderItem detach() {
        super.detach();
        parent = null;
        return this;
    }

    @Override
    protected CEntity<?> owningEntity() {
        return getParent();
    }

}
