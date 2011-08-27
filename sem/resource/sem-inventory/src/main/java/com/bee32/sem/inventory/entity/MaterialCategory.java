package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.ox1.config.BatchConfig;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

@Entity
@BatchSize(size = BatchConfig.TREE)
@SequenceGenerator(name = "idgen", sequenceName = "material_category_seq", allocationSize = 1)
public class MaterialCategory
        extends TreeEntityAuto<Integer, MaterialCategory> {

    private static final long serialVersionUID = 1L;

    CodeGenerator codeGenerator = CodeGenerator.NONE;
    List<Material> materials = new ArrayList<Material>();

    public MaterialCategory() {
        super();
    }

    public MaterialCategory(String name) {
        super(name);
    }

    public MaterialCategory(MaterialCategory parent, String name) {
        super(name);
        setParent(parent);
    }

    /**
     * 分类名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 该类别采用的代码生成器
     */
    @Transient
    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        if (codeGenerator == null)
            throw new NullPointerException("codeGenerator");
        this.codeGenerator = codeGenerator;
    }

    /**
     * 代码生成器的枚举值，以后可能会加入（表达式）构造参数，故不用 char 而用 string。
     */
    @Column(name = "codeGenerator", nullable = false)
    String get_CodeGenerator() {
        return "" + codeGenerator.getValue();
    }

    void set_CodeGenerator(String _codeGenerator) {
        if (_codeGenerator == null)
            throw new NullPointerException("_codeGenerator");

        if (_codeGenerator.isEmpty())
            throw new IllegalArgumentException("Code generator is empty.");

        char cgVal = _codeGenerator.charAt(0);
        String cgParam = _codeGenerator.substring(1);
        codeGenerator = CodeGenerator.valueOf(cgVal);
    }

    /**
     * 该类别的物料列表。
     */
    @OneToMany(mappedBy = "category")
    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        if (materials == null)
            throw new NullPointerException("materials");
        this.materials = materials;
    }

}
