package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 定单明细项目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_item_seq", allocationSize = 1)
public class MakeOrderItem
        extends AbstractItem
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int EXT_PROD_NAME_LENGTH = 30;
    public static final int EXT_SPEC_LENGTH = 30;

    MakeOrder parent;
    int index;
    Part part;
    Date deadline;

    String externalProductName;
    String externalSpecification;

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

    @NaturalId
    @ManyToOne
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
//        if (part == null)
//            throw new NullPointerException("part");
        this.part = part;
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
    public String getExternalSpecification() {
        return externalSpecification;
    }

    public void setExternalSpecification(String externalSpecification) {
        this.externalSpecification = externalSpecification;
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
                naturalId(part));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "parent", parent), //
                selector(prefix + "part", part));
    }

    @Override
    public MakeOrderItem detach() {
        super.detach();
        parent = null;
        return this;
    }

}
