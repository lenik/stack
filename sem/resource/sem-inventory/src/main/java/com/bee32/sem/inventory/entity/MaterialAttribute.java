package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;

/**
 * 用户定义的物料属性。
 * <p>
 * 这些属性仅供查看用，即不能用做搜索的关键字，也不能用于任何计算。 如果需要搜索和计算，应该在 MaterialXP 上做扩展。
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_attribute_seq", allocationSize = 1)
public class MaterialAttribute
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    String name;
    String value;

    public MaterialAttribute() {
    }

    public MaterialAttribute(Material material, String name, String value) {
        if (material == null)
            throw new NullPointerException("material");
        if (name == null)
            throw new NullPointerException("name");
        setMaterial(material);
        setName(name);
        setValue(value);
    }

    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @NaturalId
    @Column(length = 30, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 额外的属性必须不能是大段文字。大段文字应该存放到附件。
     */
    @Column(length = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        MaterialAttribute o = (MaterialAttribute) other;

        if (material == null || name == null)
            return false;

        if (!material.equals(o.material))
            return false;

        if (!name.equals(o.name))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;

        if (material == null || name == null)
            return System.identityHashCode(this);

        hash += material.hashCode();
        hash += name.hashCode();
        return hash;
    }

}
