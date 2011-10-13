package com.bee32.sem.inventory.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.tree.TreeEntityDto;
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

    @Override
    protected void _marshal(MaterialCategory source) {
        this.name = source.getName();
        this.codeGenerator = source.getCodeGenerator();
        classification = source.getClassification() == null ? null : source.getClassification().getValue();

        if (selection.contains(MATERIALS))
            this.materials = marshalList(MaterialDto.class, source.getMaterials());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        FacesMessage msg = null;
//
//        if(name == null) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "错误", "物料分类名称为空指针!");
//            ctx.addMessage(null, msg);
//            throw new NullPointerException("物料分类名称为空指针!");
//        }
//        name = TextUtil.normalizeSpace(name);
//
//        if(name.isEmpty()) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "错误", "物料分类名称为空!");
//            ctx.addMessage(null, msg);
//            throw new IllegalArgumentException("物料分类名称为空!");
//        }
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

}
