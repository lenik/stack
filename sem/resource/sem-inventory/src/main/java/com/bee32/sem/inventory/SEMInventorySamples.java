package com.bee32.sem.inventory;

import java.util.Arrays;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialAttribute;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.world.thing.SEMWorldThingSamples;
import com.bee32.sem.world.thing.Unit;

@ImportSamples({ SEMPeopleSamples.class, SEMWorldThingSamples.class })
public class SEMInventorySamples
        extends SampleContribution {

    public static Material cskdp = new Material();
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

        cskdp.setName("超时空大炮");
        cskdp.setUnit(Unit.CUBIC_METER);
        cskdp.setBarCode("1693612370098");
        cskdp.setSerial("CSKDP-01");
        cskdp.setCategory(parentCategory);
        cskdp.setPreferredLocations(Arrays.asList(preferredLocation));

        MaterialAttribute cskdpDistAttr = new MaterialAttribute(cskdp, "炮程", "120km");
        MaterialAttribute cskdpRefmaAttr = new MaterialAttribute(cskdp, "推荐手办", "ABD-432");
        cskdp.setAttributes(Arrays.asList(cskdpDistAttr, cskdpRefmaAttr));

        parentCategory.setMaterials(Arrays.asList(cskdp));
        parentCategory.setCodeGenerator(CodeGenerator.GUID);

        categoryX.setMaterials(Arrays.asList(cskdp));
        categoryX.setParent(parentCategory);
        categoryX.setCodeGenerator(CodeGenerator.NONE);

        categoryY.setMaterials(Arrays.asList(cskdp));
        categoryY.setParent(parentCategory);
        categoryY.setCodeGenerator(CodeGenerator.GUID);

        cskdpDistAttr.setMaterial(cskdp);
        cskdpDistAttr.setName("主要材料");
        cskdpDistAttr.setValue("Al合金");

        cskdpRefmaAttr.setMaterial(cskdp);
        cskdpRefmaAttr.setName("其他材料");
        cskdpRefmaAttr.setValue("凝气胶,巴基球等");

        preferredLocation.setMaterial(cskdp);
        preferredLocation.setBatch("cshdp120st");
        preferredLocation.setLocation(null);
        preferredLocation.setComment("...那啥?");

    }

    @Override
    protected void preamble() {
        add(stockWarehouse);
        add(parentStockLocation);
        add(hokaidou);

        add(parentCategory);
        add(categoryX);
        add(categoryY);
        add(cskdp);
        add(preferredLocation);
    }

}
