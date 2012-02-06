package com.bee32.sem.inventory.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.config.BatchConfig;
import com.bee32.plover.ox1.tree.TreeEntityAuto;
import com.bee32.sem.inventory.Classification;

@Entity
@BatchSize(size = BatchConfig.TREE)
@SequenceGenerator(name = "idgen", sequenceName = "material_category_seq", allocationSize = 1)
public class MaterialCategory
        extends TreeEntityAuto<Integer, MaterialCategory> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 40;

    CodeGenerator codeGenerator = CodeGenerator.NONE;
    List<Material> materials = new ArrayList<Material>();
    Classification classification = Classification.OTHER;

    int materialCount;

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

    @Transient
    protected boolean isUniqueChildren() {
        return true;
    }

    /**
     * 分类名称
     */
    @Column(length = NAME_LENGTH)
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
        // String cgParam = _codeGenerator.substring(1);
        codeGenerator = CodeGenerator.valueOf(cgVal);
    }

    /**
     * 该类别的物料列表。
     */
    @OneToMany(mappedBy = "category")
    public List<Material> getMaterials() {
        return materials;
    }

    @Formula("(select count(*) from material m where m.category=id)")
    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }

    public void setMaterials(List<Material> materials) {
        if (materials == null)
            throw new NullPointerException("materials");
        this.materials = materials;
    }

    public void addMaterial(Material material) {
        if (!materials.contains(material)) {
            materials.add(material);
        }
    }

    public void removeMaterial(Material material) {
        if (materials.contains(material)) {
            materials.remove(material);
        }
    }

    @Column(name = "classification")
    char get_classification() {
        return classification.getValue();
    }

    void set_classification(char _classification) {
        classification = Classification.valueOf(_classification);
    }

    /**
     * 类型
     */
    @Transient
    public Classification getClassification() {
        return classification;
    }

    /**
     * 类型
     */
    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    @Override
    protected Serializable naturalId() {
        Integer parentId;
        if (getParent() == null)
            parentId = null;
        else
            parentId = getParent().getId();

        return new IdComposite(//
                parentId, //
                name);
    }

    @Override
    protected ICriteriaElement localSelector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
