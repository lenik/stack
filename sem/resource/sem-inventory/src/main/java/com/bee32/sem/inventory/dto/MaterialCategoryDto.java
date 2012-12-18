package com.bee32.sem.inventory.dto;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;

public class MaterialCategoryDto
        extends TreeEntityDto<MaterialCategory, Integer, MaterialCategoryDto> {

    private static final long serialVersionUID = 1L;

    public static final int MATERIALS = 0x01000000;
    public static final int PART_COUNTS = 0x02000000;

    String name;
    List<MaterialDto> materials;
    int materialCount;
    int partCount;
    int topPartCount;
    CodeGenerator codeGenerator;
    MaterialType materialType;

    @Override
    protected void _marshal(MaterialCategory source) {
        this.codeGenerator = source.getCodeGenerator();
        this.materialType = source.getMaterialType();

        if (selection.contains(MATERIALS))
            this.materials = mrefList(MaterialDto.class, -1, source.getMaterials());
        else
            this.materials = Collections.emptyList();

        this.materialCount = source.getMaterialCount();

        if (selection.contains(PART_COUNTS)) {
            // TODO @Formula("(select count(*) from part p where p.category=id)")
            // TODO @Formula("(select count(*) from part p where p.category=id and p.id not in (select distinct pi.part from part_item pi where pi.part is not null))")
            // this.partCount = source.getPartCount();
            // this.topPartCount = source.getTopPartCount();
            this.partCount = 0;
            this.topPartCount = 0;
        }
    }

    @Override
    protected void _unmarshalTo(MaterialCategory target) {
        target.setCodeGenerator(codeGenerator);
        target.setMaterialType(materialType);

        if (selection.contains(MATERIALS))
            mergeList(target, "materials", materials);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        char _cg = map.getChar("codeGenerator");
        codeGenerator = CodeGenerator.forValue(_cg);
        char _materialType = map.getChar("materialType");
        materialType = MaterialType.forValue(_materialType);
    }

    @Override
    protected boolean isUniqueChildren() {
        return true;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        if (materials == null)
            throw new NullPointerException("materials");
        this.materials = materials;
    }

    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }

    public int getTotalMaterialCount() {
        int total = materialCount;
        for (MaterialCategoryDto child : getChildren())
            total += child.getTotalMaterialCount();
        return total;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public int getTotalPartCount() {
        int total = partCount;
        for (MaterialCategoryDto child : getChildren())
            total += child.getTotalPartCount();
        return total;
    }

    public int getTopPartCount() {
        return topPartCount;
    }

    public void setTopPartCount(int topPartCount) {
        this.topPartCount = topPartCount;
    }

    public int getTotalTopPartCount() {
        int total = topPartCount;
        for (MaterialCategoryDto child : getChildren())
            total += child.getTotalTopPartCount();
        return total;
    }

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        if (codeGenerator == null)
            throw new NullPointerException("codeGenerator");
        this.codeGenerator = codeGenerator;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        if (materialType == null)
            throw new NullPointerException("materialType");
        this.materialType = materialType;
    }

    public char getMaterialTypeValue() {
        return materialType.getValue();
    }

    public void setMaterialTypeValue(char materialTypeValue) {
        this.materialType = MaterialType.forValue(materialTypeValue);
    }

}
