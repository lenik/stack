package com.bee32.sem.inventory;

import java.util.Arrays;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialAttribute;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.world.thing.SEMWorldSamples;
import com.bee32.sem.world.thing.Unit;

@ImportSamples({ SEMPeopleSamples.class, SEMWorldSamples.class })
public class SEMInventorySamples
        extends EntitySamplesContribution {

    public static Material material = new Material();
    public static MaterialCategory parentCategory = new MaterialCategory();
    public static MaterialCategory categoryX = new MaterialCategory();
    public static MaterialCategory categoryY = new MaterialCategory();
    public static MaterialPreferredLocation preferredLocation = new MaterialPreferredLocation();
    public static StockWarehouse stockWarehouse = new StockWarehouse();
    public static StockLocation parentStockLocation = new StockLocation();
    public static StockLocation hokaidou = new StockLocation();

    static {
        stockWarehouse.setAddress("日本");
        stockWarehouse.setPhone("911");
        stockWarehouse.setManager(SEMPeopleSamples.jackPerson);

        parentStockLocation.setAddress("圣神布里塔尼亚帝国-11区");
        parentStockLocation.setWarehouse(stockWarehouse);

        hokaidou.setAddress("北海道第一仓库");
        hokaidou.setWarehouse(stockWarehouse);
        hokaidou.setParent(parentStockLocation);

        MaterialAttribute attribute = new MaterialAttribute();
        MaterialAttribute attribute1 = new MaterialAttribute();
        material.setName("超时空大炮");
        material.setUnit(Unit.CUBIC_METER);
        material.setUnitConv(SEMWorldSamples.baseConv);
        material.setBarCode("I693612370098");
        material.setSerial("超时空大炮");
        material.setCategory(parentCategory);
        material.setAttributes(Arrays.asList(attribute, attribute1));
        material.setPreferredLocations(Arrays.asList(preferredLocation));

        parentCategory.setMaterials(Arrays.asList(material));
        parentCategory.setCodeGenerator(CodeGenerator.GUID);

        categoryX.setMaterials(Arrays.asList(material));
        categoryX.setParent(parentCategory);
        categoryX.setCodeGenerator(CodeGenerator.NONE);

        categoryY.setMaterials(Arrays.asList(material));
        categoryY.setParent(parentCategory);
        categoryY.setCodeGenerator(CodeGenerator.GUID);

        attribute.setMaterial(material);
        attribute.setName("主要材料");
        attribute.setValue("Al合金");

        attribute1.setMaterial(material);
        attribute1.setName("其他材料");
        attribute1.setValue("凝气胶,巴基球等");

        preferredLocation.setMaterial(material);
        preferredLocation.setBatch("cshdp120st");
        preferredLocation.setLocation(null);
        preferredLocation.setComment("...那啥?");

    }

    @Override
    protected void preamble() {
        addNormalSample(stockWarehouse);
        addNormalSample(parentStockLocation);
        addNormalSample(hokaidou);

        addNormalSample(parentCategory);
        addNormalSample(categoryX);
        addNormalSample(categoryY);
        addNormalSample(material);
        addNormalSample(preferredLocation);
    }

}
