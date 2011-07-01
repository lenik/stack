package com.bee32.sem.material.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class MaterialCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    MaterialCategory parent;
    List<MaterialCategory> children = new ArrayList<MaterialCategory>();
    List<Material> materials;

    AutoCodingStyle autoCoding = AutoCodingStyle.NONE;

    @ManyToOne
    public MaterialCategory getParent() {
        return parent;
    }

    public void setParent(MaterialCategory parent) {
        this.parent = parent;
    }

    @OneToMany
    public List<MaterialCategory> getChildren() {
        return children;
    }

    public void setChildren(List<MaterialCategory> children) {
        this.children = children;
    }

    @OneToMany
    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @Column(name = "autoCoding", nullable = false)
    char getAutoCoding_() {
        return autoCoding.getValue();
    }

    void setAutoCoding_(char autoCoding) {
        // TODO autoCoding = ..
    }

    @Transient
    public AutoCodingStyle getAutoCoding() {
        return autoCoding;
    }

    public void setAutoCoding(AutoCodingStyle autoCoding) {
        this.autoCoding = autoCoding;
    }

}
