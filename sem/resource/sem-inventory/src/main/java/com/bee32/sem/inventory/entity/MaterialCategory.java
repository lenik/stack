package com.bee32.sem.inventory.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.orm.ext.config.BatchConfig;
import com.bee32.plover.orm.ext.tree.TreeEntityAuto;

@Entity
@BatchSize(size = BatchConfig.TREE)
public class MaterialCategory
        extends TreeEntityAuto<Long, MaterialCategory> {

    private static final long serialVersionUID = 1L;

    List<Material> materials;

    CodeGenerator codeGenerator = CodeGenerator.NONE;

    @OneToMany(mappedBy = "category")
    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        if (materials == null)
            throw new NullPointerException("materials");
        this.materials = materials;
    }

    @Column(name = "codeGenerator", nullable = false)
    char get_CodeGenerator() {
        return codeGenerator.getValue();
    }

    void set_CodeGenerator(char _codeGenerator) {
        codeGenerator = CodeGenerator.valueOf(_codeGenerator);
    }

    @Transient
    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        if (codeGenerator == null)
            throw new NullPointerException("codeGenerator");
        this.codeGenerator = codeGenerator;
    }

}
