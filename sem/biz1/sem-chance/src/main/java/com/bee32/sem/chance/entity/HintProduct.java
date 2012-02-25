package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;

/**
 * 选型明细
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "hint_product_seq", allocationSize = 1)
public class HintProduct
        extends UIEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 100;
    public static final int SPEC_LENGTH = 100;
    public static final int UNIT_NAME_LENGTH = 30;

    Chance chance;
    String productName;
    String modelSpec;
    String unitName;
    List<HintProductAttribute> attributes = new ArrayList<>();
    List<HintProductQuotation> quotations = new ArrayList<>();
    Material decidedMaterial;

    @ManyToOne(optional = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        if (chance == null)
            throw new NullPointerException("chance");
        this.chance = chance;
    }

    /**
     * 选型产品的名称（外部名称）
     */
    @Column(length = NAME_LENGTH)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 选型产品的规格（外部规格）
     */
    @Column(length = SPEC_LENGTH)
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    /**
     * 选型产品的单位(外部单位)
     */
    @Column(length = UNIT_NAME_LENGTH)
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * 选型产品的附加属性
     */
    @OneToMany(mappedBy = "product")
    @Cascade(CascadeType.ALL)
    public List<HintProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<HintProductAttribute> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    /**
     * 选型产品对应的报价记录
     */
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<HintProductQuotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<HintProductQuotation> quotations) {
        if (quotations == null)
            throw new NullPointerException("quotations");
        this.quotations = quotations;
    }

    /**
     * 选型产品对应的内部物料，选型时，由技术员进行确定
     */
    @ManyToOne
    public Material getDecidedMaterial() {
        return decidedMaterial;
    }

    public void setDecidedMaterial(Material decidedMaterial) {
        this.decidedMaterial = decidedMaterial;
    }

}
