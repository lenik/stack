package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;

/**
 * 选型产品的附加属性
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "wanted_product_attribute_seq", allocationSize = 1)
public class WantedProductAttribute
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 50;
    public static final int VALUE_LENGTH = 100;

    WantedProduct product;

    String name;
    String value;

    @ManyToOne(optional = false)
    public WantedProduct getProduct() {
        return product;
    }

    public void setProduct(WantedProduct product) {
        if (product == null)
            throw new NullPointerException("product");
        this.product = product;
    }

    /**
     * 属性名称
     */
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    /**
     * 属性值
     *
     * @return
     */
    @Column(length = VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
