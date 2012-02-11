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

@Entity
@BatchSize(size = BatchConfig.TREE)
@SequenceGenerator(name = "idgen", sequenceName = "material_category_seq", allocationSize = 1)
public class MaterialCategory
        extends TreeEntityAuto<Integer, MaterialCategory> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 40;

    CodeGenerator codeGenerator = CodeGenerator.NONE;
    MaterialType materialType = MaterialType.OTHER;
    List<Material> materials = new ArrayList<Material>();

    int materialCount;

    public MaterialCategory() {
        super();
    }

    public MaterialCategory(MaterialType type, String name) {
        this(null, type, name);
    }

    public MaterialCategory(MaterialCategory parent, MaterialType type, String name) {
        super(parent, name);
        if (type == null)
            throw new NullPointerException("type");
        this.materialType = type;
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

    @Column(name = "material_type", nullable = false)
    char get_materialType() {
        return materialType.getValue();
    }

    void set_materialType(char _materialType) {
        materialType = MaterialType.valueOf(_materialType);
    }

    /**
     * 类型
     */
    @Transient
    public MaterialType getMaterialType() {
        return materialType;
    }

    /**
     * 类型
     */
    public void setMaterialType(MaterialType materialType) {
        if (materialType == null)
            throw new NullPointerException("materialType");
        this.materialType = materialType;
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
