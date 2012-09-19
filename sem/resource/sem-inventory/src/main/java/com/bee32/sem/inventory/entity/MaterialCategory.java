package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import com.bee32.plover.ox1.config.BatchConfig;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 物料分类
 *
 * 物料的类别。
 */
@Entity
@BatchSize(size = BatchConfig.TREE)
@SequenceGenerator(name = "idgen", sequenceName = "material_category_seq", allocationSize = 1)
public class MaterialCategory
        extends TreeEntityAuto<Integer, MaterialCategory> {

    private static final long serialVersionUID = 1L;

    CodeGenerator codeGenerator = CodeGenerator.NONE;
    MaterialType materialType = MaterialType.OTHER;
    List<Material> materials = new ArrayList<Material>();

    int materialCount;
    int partCount;
    int topPartCount;

    public MaterialCategory() {
        super();
    }

    public MaterialCategory(MaterialType type, String label) {
        this(null, type, label);
    }

    public MaterialCategory(MaterialCategory parent, MaterialType materialType, String label) {
        super(parent);
        if (materialType == null)
            throw new NullPointerException("materialType");
        this.materialType = materialType;
        this.label = label;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof MaterialCategory)
            _populate((MaterialCategory) source);
        else
            super.populate(source);
    }

    protected void _populate(MaterialCategory o) {
        super._populate(o);
        codeGenerator = o.codeGenerator;
        materialType = o.materialType;
        materials = new ArrayList<Material>(materials);
        materialCount = o.materialCount;
        partCount = o.partCount;
    }

    @Transient
    protected boolean isUniqueChildren() {
        return true;
    }

    /**
     * 生成器
     *
     * 该类别采用的代码生成器。
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
     * 生成器
     *
     * 该类别采用的代码生成器。
     *
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
        codeGenerator = CodeGenerator.forValue(cgVal);
    }

    /**
     * 物料集
     *
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

    /**
     * 物料计数
     *
     * 该类别物料的数目。
     */
    @Formula("(select count(*) from material m where m.category=id)")
    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }

    /**
     * 组件计数
     *
     * 该类别组件的数目。
     */
    @Formula("(select count(*) from part p where p.category=id)")
    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    /**
     * 顶层bom数
     */
    @Formula("(select count(*) from part p where p.category=id and p.id not in (select distinct pi.part from part_item pi where pi.part is not null))")
    public int getTopPartCount() {
        return topPartCount;
    }

    public void setTopPartCount(int topPartCount) {
        this.topPartCount = topPartCount;
    }

    /**
     * 形式类型
     *
     * 物料的形式，如原材料(r)、成品(p)或半成品(s)。
     */
    @Column(name = "material_type", nullable = false)
    public char get_materialType() {
        return materialType.getValue();
    }

    public void set_materialType(char _materialType) {
        materialType = MaterialType.forValue(_materialType);
    }

    /**
     * 形式类型
     *
     * 物料的形式，如原材料(r)、成品(p)或半成品(s)。
     */
    @Transient
    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        if (materialType == null)
            throw new NullPointerException("materialType");
        this.materialType = materialType;
    }

}
