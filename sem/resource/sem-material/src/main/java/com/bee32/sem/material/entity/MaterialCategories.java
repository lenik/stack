package com.bee32.sem.material.entity;

import com.bee32.plover.orm.sample.StandardSamples;

/**
 * 物料分类样本
 *
 * <p lang="en">
 */
public class MaterialCategories
        extends StandardSamples {

    public final MaterialCategory component = new MaterialCategory();
    public final MaterialCategory part = new MaterialCategory();
    public final MaterialCategory rawMaterial = new MaterialCategory();

    @Override
    protected void wireUp() {
        component.setLabel("构件");
        component.setMaterialType(MaterialType.PRODUCT);

        part.setLabel("零件");
        part.setMaterialType(MaterialType.SEMI);

        rawMaterial.setLabel("原材料");
        rawMaterial.setMaterialType(MaterialType.RAW);
    }

}
