package com.bee32.sem.inventory.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.MaterialCategory;

public class MaterialCategoryDto
        extends TreeEntityDto<MaterialCategory, Long, MaterialCategoryDto> {

    private static final long serialVersionUID = 1L;

    public static final int MATERIALS = 1;

    private String name;
    private List<MaterialDto> materials;
    private CodeGenerator codeGenerator;

    @Override
    protected void _marshal(MaterialCategory source) {
        this.name = source.getName();
        this.codeGenerator = source.getCodeGenerator();
        if (selection.contains(MATERIALS))
            this.materials = marshalList(MaterialDto.class, MaterialDto.ATTRBUTES, source.getMaterials());
    }

    @Override
    protected void _unmarshalTo(MaterialCategory target) {
        target.setName(name);
        target.setCodeGenerator(codeGenerator);

        if (selection.contains(MATERIALS))
            mergeList(target, "materials", materials);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public void addMaterial(MaterialDto material) {
        materials.add(material);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        if (codeGenerator == null)
            throw new NullPointerException("codeGenerator");
        this.codeGenerator = codeGenerator;
    }

}
