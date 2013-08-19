package com.bee32.sem.material.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * 物料自定义属性
 *
 * 用户定义的物料属性。 这些属性仅供查看用，即不用做搜索的关键字，也不能用于任何计算。 如果需要搜索和计算，需要在 MaterialXP 上做扩展。
 *
 * <p lang="en">
 * Material Attribute
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_attribute_seq", allocationSize = 1)
public class MaterialAttribute
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;
    public static final int VALUE_LENGTH = 100;

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

    @Override
    public void populate(Object source) {
        if (source instanceof MaterialAttribute)
            _populate((MaterialAttribute) source);
        else
            super.populate(source);
    }

    protected void _populate(MaterialAttribute o) {
        super._populate(o);
        material = o.material;
        name = o.name;
        value = o.value;
    }

    /**
     * 物料
     *
     * 声明的物料。
     */
    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 名称
     *
     * 属性的名称（不可重复）。
     */
    @NaturalId
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 内容
     *
     * 属性的内容。 额外的属性必须不能是大段文字。大段文字应该存放到附件。
     */
    @Column(length = VALUE_LENGTH)
    @Index(name = "MaterialAttributeValue")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(material), //
                name);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (material == null)
            throw new NullPointerException("material");
        if (name == null)
            throw new NullPointerException("name");
        return selectors(//
                selector(prefix + "material", material), //
                new Equals(prefix + "name", name));
    }

}
