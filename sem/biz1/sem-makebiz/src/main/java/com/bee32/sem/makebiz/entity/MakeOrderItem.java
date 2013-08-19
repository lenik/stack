package com.bee32.sem.makebiz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 生产订单明细
 *
 * 生产订单上的明细项目。
 *
 * <p lang="en">
 * Make Order Item
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

    boolean nameplate = true;

    String externalProductName;
    String externalModelSpec;
    String externalUnit;

    List<DeliveryNoteItem> deliveryNoteItems;

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
        nameplate = o.nameplate;
        externalProductName = o.externalProductName;
        externalModelSpec = o.externalModelSpec;
        externalUnit = o.externalUnit;
        deliveryNoteItems = o.deliveryNoteItems;

    }

    /**
     * 订单
     *
     * 订单明细对应的主控类。
     */
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

    /**
     * 物料
     *
     * 订单明细对应的物料（产品）。
     */
    @NaturalId(mutable = true)
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 交货时间
     *
     * 交货期限。
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * 产品外部名称
     *
     * 和某个客户对应，具体对应客户在MakeOrder中。某个产品对于不同客户的不同叫法，对内为同一种产品(同一个物料)。
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
     *
     * 和某个客户对应，具体对应客户在MakeOrder中。不同的客户对某个产品有不同的技术要求，但对内为同一个产品，所以技术要求相同。
     */
    @Column(length = EXT_SPEC_LENGTH)
    public String getExternalModelSpec() {
        return externalModelSpec;
    }

    public void setExternalModelSpec(String externalModelSpec) {
        this.externalModelSpec = externalModelSpec;
    }

    /**
     * 外部单位
     *
     * 产品在外部名称时对应的单位。
     */
    @Column(length = EXT_UNIT_LENGTH)
    public String getExternalUnit() {
        return externalUnit;
    }

    public void setExternalUnit(String externalUnit) {
        this.externalUnit = externalUnit;
    }

    /**
     * 铭牌是否使用内部名称
     *
     * 产品在出厂时，如果需要贴上铭牌的，标记铭牌上是使用物料名称还是外部名称。true-使用物料名称，规格，参数; false-使用外部物料名称，规格，参数。
     */
    @DefaultValue("true")
    @Column(nullable = false)
    public boolean isNameplate() {
        return nameplate;
    }

    public void setNameplate(boolean nameplate) {
        this.nameplate = nameplate;
    }

    /**
     * 汇率日期
     *
     * 汇率每天变动，这里指定汇率的取数时间点。
     */
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

    /**
     * 送货明细项目
     *
     * 一个订单明细对应多个送货明细项目，这样就可以看到送货情况了。
     */
    @OneToMany(mappedBy = "orderItem")
    public List<DeliveryNoteItem> getDeliveryNoteItems() {
        return deliveryNoteItems;
    }

    public void setDeliveryNoteItems(List<DeliveryNoteItem> deliveryNoteItems) {
        this.deliveryNoteItems = deliveryNoteItems;
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
