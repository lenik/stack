package com.bee32.sem.material.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CollectionOfElements;

import com.bee32.sem.file.blob.FileBlob;
import com.bee32.sem.store.entity.Thing;

@Entity
public class Material
        extends Thing<MaterialXP> {

    private static final long serialVersionUID = 1L;

    MaterialCategory category;

    List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();
    List<FileBlob> accessories = new ArrayList<FileBlob>();

    // 常用属性。
    // 这些属性和单位还算无关。
    // String packageSize;
    // String packageWeight;

    @ManyToOne(optional = false)
    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    /**
     * 物料附加属性。
     */
    public List<MaterialAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MaterialAttribute> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    /**
     * 附件文件。
     */
    @CollectionOfElements
    public List<FileBlob> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<FileBlob> accessories) {
        if (accessories == null)
            throw new NullPointerException("accessories");
        this.accessories = accessories;
    }

}
