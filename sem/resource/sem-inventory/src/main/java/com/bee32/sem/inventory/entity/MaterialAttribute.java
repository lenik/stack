package com.bee32.sem.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * 用户定义的物料属性。
 * <p>
 * 这些属性仅供查看用，即不能用做搜索的关键字，也不能用于任何计算。 如果需要搜索和计算，应该在 MaterialXP 上做扩展。
 */
@Entity
@Blue
@DefaultPermission(Permission.R_X)
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

    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @NaturalId
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 额外的属性必须不能是大段文字。大段文字应该存放到附件。
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
        return new IdComposite(naturalId(material), name);
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
