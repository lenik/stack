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

    CodeGenerator codeGenerator = CodeGenerator.NONE;

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

    @Column(name = "codeGenerator", nullable = false)
    char get_CodeGenerator() {
        return codeGenerator.getValue();
    }

    void set_CodeGeneratorVal(char _codeGenerator) {
        codeGenerator = CodeGenerator.valueOf(_codeGenerator);
    }

    @Transient
    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

}
