package com.bee32.sem.chance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;

/**
 * 选型明细
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "lectotype_item_seq", allocationSize = 1)
public class LectotypeItem
    extends UIEntityAuto<Long>
    implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 100;
    public static final int SPEC_LENGTH = 100;
    public static final int UNIT_LENGTH = 30;

    LectotypeItem parent;

    String name;
    String spec;
    String unit;

    BigDecimal quantity = new BigDecimal(1);

    List<LectotypeAttribute> attrs;
    List<Quotation> quotations;

    Material material;

    @NaturalId
    @ManyToOne(optional = false)
    public LectotypeItem getParent() {
        return parent;
    }

    public void setParent(LectotypeItem parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    /**
     * 选型产品的名称（外部名称）
     */
    @Column(length = NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 选型产品的规格（外部规格）
     * @return
     */
    @Column(length = SPEC_LENGTH)
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 选型产品的单位(外部单位)
     * @return
     */
    @Column(length = UNIT_LENGTH)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 数量
     * @return
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * 选型产品的附加属性
     * @return
     */
    @OneToMany(mappedBy = "lectotypeItem")
    @Cascade(CascadeType.ALL)
    public List<LectotypeAttribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<LectotypeAttribute> attrs) {
        this.attrs = attrs;
    }

    /**
     * 选型产品对应的报价记录
     * @return
     */
    @OneToMany(mappedBy = "lectotypeItem")
    @Cascade(CascadeType.ALL)
    public List<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<Quotation> quotations) {
        this.quotations = quotations;
    }

    /**
     * 选型产品对应的内部物料，选型时，由技术员进行确定
     * @return
     */
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent));
    }
}
