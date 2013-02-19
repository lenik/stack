package com.bee32.sem.makebiz.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.entity.MakeStepModel;
import com.bee32.sem.make.entity.MakeStepNames;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategories;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.Units;

public class MaterialBatchImportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceDto target;
    String partData;

    // 568127153575

    public void doImport() {
        if (target == null) {
            uiLogger.warn("请选择对应销售机会");
            return;
        }
        Units units = BEAN(Units.class);
        MaterialCategories categories = BEAN(MaterialCategories.class);
        MakeStepNames stepnames = BEAN(MakeStepNames.class);

        List<UnitConv> convs = new ArrayList<UnitConv>();
        List<Material> materials = new ArrayList<Material>();
        List<Part> parts_bom = new ArrayList<Part>();

        String[] strings = partData.split("#");
        String compoent_string = strings[0];
        String mix_string = strings[1];

        String[] comp_item = compoent_string.split(",");
        Material compmaterial = DATA(Material.class).getUnique(
                MakebizCriteria.existingMaterialCheck(comp_item[0], comp_item[1]));
        if (compmaterial == null) {
            compmaterial = new Material();
            compmaterial.setLabel(comp_item[0]);
            compmaterial.setModelSpec(comp_item[1]);
            compmaterial.setUnit(units.PIECE);
            compmaterial.setCategory(BEAN(MaterialCategories.class).component);
            UnitConv conv = new UnitConv();
            conv.setUnit(units.PIECE);
            conv.getScaleMap().put(units.KILOGRAM, Double.parseDouble(comp_item[2]));
            conv.getScaleMap().put(units.SQUARE_METER, Double.parseDouble(comp_item[3]));
            compmaterial.setUnitConv(conv);
            convs.add(conv);
            materials.add(compmaterial);
        }

        Part parent = DATA(Part.class).getUnique(
                MakebizCriteria.existingPartCheck(compmaterial.getLabel(), compmaterial.getModelSpec()));

        if (parent == null) {
            parent = new Part();
            parent.setChance(target.unmarshal());
            parent.setTarget(compmaterial);
            parent.setCategory(categories.component);

            // make step model
            MakeStepModel step1 = new MakeStepModel();
            step1.setStepName(stepnames.drilling);
            step1.setOrder(1);
            step1.setOutput(parent);

            MakeStepModel step2 = new MakeStepModel();
            step2.setStepName(stepnames.assembly);
            step2.setOrder(2);
            step2.setOutput(parent);

            MakeStepModel step3 = new MakeStepModel();
            step3.setStepName(stepnames.welding);
            step3.setOrder(3);
            step3.setOutput(parent);

            MakeStepModel step4 = new MakeStepModel();
            step4.setStepName(stepnames.metalPlating);
            step4.setOrder(4);
            step4.setOutput(parent);

            MakeStepModel step5 = new MakeStepModel();
            step5.setStepName(stepnames.sandblast);
            step5.setOrder(5);
            step5.setOutput(parent);

            MakeStepModel step6 = new MakeStepModel();
            step6.setStepName(stepnames.painting);
            step6.setOrder(6);
            step6.setOutput(parent);

            MakeStepModel step7 = new MakeStepModel();
            step7.setStepName(stepnames.loading);
            step7.setOrder(7);
            step7.setOutput(parent);

            parent.setSteps(Arrays.asList(step1, step2, step3, step4, step5, step6, step7));

            parts_bom.add(parent);
        }

        List<PartItem> children = new ArrayList<PartItem>();

        String[] parts = mix_string.split(";");

        for (String p : parts) {

            int index = p.indexOf("/");
            if (index < 0)
                continue;

            String[] split = p.split("/");
            String part_string = split[0]; // 零件
            String rawmaterial_string = split[1]; // 物料

            if (rawmaterial_string.length() <= 0)
                continue;

            String[] raw_item = rawmaterial_string.split(",");

            Material rawmaterial = DATA(Material.class).getUnique(
                    MakebizCriteria.existingMaterialCheck(raw_item[0], raw_item[1]));

            if (rawmaterial == null) {
                rawmaterial = new Material();
                rawmaterial.setLabel(raw_item[0]);
                rawmaterial.setModelSpec(raw_item[1]);
                rawmaterial.setUnit(units.MILLIMETER);
                rawmaterial.setCategory(categories.rawMaterial);
                materials.add(rawmaterial);

            }

            if (part_string.length() <= 0)
                continue;

            String[] part_item = part_string.split(",");
            Material partmaterial = DATA(Material.class).getUnique(
                    MakebizCriteria.existingMaterialCheck(part_item[0], part_item[1]));
            Part y = DATA(Part.class).getUnique(MakebizCriteria.existingPartCheck(part_item[0], part_item[1]));
            if (partmaterial == null) {
                partmaterial = new Material();
                partmaterial.setLabel(part_item[0]);
                partmaterial.setModelSpec(part_item[1]);
                partmaterial.setUnit(units.PIECE);
                partmaterial.setCategory(categories.part);
                UnitConv conv = new UnitConv();
                conv.setUnit(units.PIECE);
                conv.getScaleMap().put(units.KILOMETER, Double.parseDouble(part_item[3]));
                conv.getScaleMap().put(units.SQUARE_METER, Double.parseDouble(part_item[4]));
                partmaterial.setUnitConv(conv);
                convs.add(conv);
                materials.add(partmaterial);
            }

            if (y == null) {

                PartItem x = new PartItem();
                x.setMaterial(rawmaterial);
                x.setPart(null);

                y = new Part();
                y.setTarget(partmaterial);
                y.setCategory(categories.part);
                y.setChildren(Arrays.asList(x));
                x.setParent(y);

                MakeStepModel cutting = new MakeStepModel();
                cutting.setStepName(stepnames.cutting);
                cutting.setOrder(1);
                cutting.setOutput(y);

                y.setSteps(Arrays.asList(cutting));

                parts_bom.add(y);

                PartItem z = new PartItem();
                z.setPart(y);
                z.setQuantity(new BigDecimal(part_item[2]));
                z.setMaterial(null);
                z.setParent(parent);

                children.add(z);
            }

        }

        parent.setChildren(children);

        if (convs.size() > 0) {
            DATA(UnitConv.class).saveAll(convs);
            uiLogger.info("导入单位换算表成功");
        } else
            uiLogger.info("没有需要导入的单位换算表");
        if (materials.size() > 0) {
            DATA(Material.class).saveAll(materials);
            uiLogger.info("导入物料成功");
        } else
            uiLogger.info("没有需要导入的物料");
        if (parts_bom.size() > 0) {
            DATA(Part.class).saveAll(parts_bom);
            uiLogger.info("导入BOM成功");
        } else {
            uiLogger.info("没有需要导入的bom");
        }

    }

    public ChanceDto getTarget() {
        return target;
    }

    public void setTarget(ChanceDto target) {
        this.target = target;
    }

    public String getPartData() {
        return partData;
    }

    public void setPartData(String partData) {
        this.partData = partData;
    }

}
