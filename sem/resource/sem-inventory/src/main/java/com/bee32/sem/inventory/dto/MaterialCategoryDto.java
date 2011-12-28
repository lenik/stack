package com.bee32.sem.inventory.dto;

import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.MaterialCategory;

public class MaterialCategoryDto
        extends TreeEntityDto<MaterialCategory, Integer, MaterialCategoryDto> {

    private static final long serialVersionUID = 1L;

    public static final int MATERIALS = 1;

    private String name;
    private List<MaterialDto> materials;
    private CodeGenerator codeGenerator;
    char classification;

    int materialCount;

    @Override
    protected void _marshal(MaterialCategory source) {
        this.name = source.getName();
        this.codeGenerator = source.getCodeGenerator();
        classification = source.getClassification() == null ? null : source.getClassification().getValue();

        if (selection.contains(MATERIALS))
            this.materials = marshalList(MaterialDto.class, source.getMaterials());

        this.materialCount = source.getMaterialCount();
    }

    @Override
    protected void _unmarshalTo(MaterialCategory target) {
        target.setName(name);
        target.setCodeGenerator(codeGenerator);
        target.setClassification(Classification.valueOf(classification));

        if (selection.contains(MATERIALS))
            mergeList(target, "materials", materials);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");

        char _cg = map.getChar("codeGenerator");
        codeGenerator = CodeGenerator.valueOf(_cg);
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

    public void addMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        materials.add(material);
    }

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        if (codeGenerator == null)
            throw new NullPointerException("codeGenerator");
        this.codeGenerator = codeGenerator;
    }


    public char getClassification() {
        return classification;
    }

    public void setClassification(char classification) {
        this.classification = classification;
    }

    public String getClassificationText() {
        return Classification.valueOf(classification).getDisplayName();
    }

    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }



}
