package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.thing.Thing;

/**
 * 选型明细
 *
 * 注：label, modelSpec 分别对应选型产品的外部名称 、和外部规格。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "hint_product_seq", allocationSize = 1)
public class WantedProduct
        extends Thing<WantedProductXP>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Chance chance;
    List<WantedProductAttribute> attributes = new ArrayList<>();
    List<WantedProductQuotation> quotations = new ArrayList<>();
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
     * 选型产品的附加属性
     */
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<WantedProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<WantedProductAttribute> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    /**
     * 选型产品对应的报价记录
     */
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<WantedProductQuotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<WantedProductQuotation> quotations) {
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
