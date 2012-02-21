package com.bee32.sem.inventory.dto;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;

public class MaterialCategoryDto
        extends TreeEntityDto<MaterialCategory, Integer, MaterialCategoryDto> {

    private static final long serialVersionUID = 1L;

    public static final int MATERIALS = 1;

    String name;
    List<MaterialDto> materials;
    int materialCount;
    int partCount;
    CodeGenerator codeGenerator;
    MaterialType materialType;

    @Override
    protected void _marshal(MaterialCategory source) {
        this.name = source.getName();
        this.codeGenerator = source.getCodeGenerator();
        this.materialType = source.getMaterialType();

        if (selection.contains(MATERIALS))
            this.materials = mrefList(MaterialDto.class, -1, source.getMaterials());
        else
            this.materials = Collections.emptyList();

        this.materialCount = source.getMaterialCount();
        this.partCount = source.getPartCount();
    }

    @Override
    protected void _unmarshalTo(MaterialCategory target) {
        target.setName(name);
        target.setCodeGenerator(codeGenerator);
        target.setMaterialType(materialType);

        if (selection.contains(MATERIALS))
            mergeList(target, "materials", materials);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");

        char _cg = map.getChar("codeGenerator");
        codeGenerator = CodeGenerator.valueOf(_cg);
        char _materialType = map.getChar("materialType");
        materialType = MaterialType.valueOf(_materialType);
    }

    @Override
    protected boolean isUniqueChildren() {
        return true;
    }

    @NotNull
    @NLength(min = 1, max = MaterialCategory.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = TextUtil.normalizeSpace(name);
        this.name = name;
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
        this.materialType = MaterialType.valueOf(materialTypeValue);
    }

}
