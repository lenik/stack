package com.bee32.sem.inventory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialAttribute;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicy;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.test.DateSamples;
import com.bee32.sem.world.SEMWorldSamples;
import com.bee32.sem.world.thing.Unit;

@ImportSamples({ SEMPeopleSamples.class, SEMWorldSamples.class, SEMVerifyPolicySamples.class })
public class SEMInventorySamples
        extends SampleContribution {

    public static StockOrderVerifyPolicy stockPolicy = new StockOrderVerifyPolicy();

    public static StockWarehouse tokyoWarehouse = new StockWarehouse();
    public static StockLocation area1 = new StockLocation();
    public static StockLocation area2 = new StockLocation();
    public static StockLocation hokaidou = new StockLocation();

    public static StockWarehouse rawWarehouse = new StockWarehouse();
    public static StockLocation loc1 = new StockLocation();
    public static StockLocation loc11 = new StockLocation();

    public static Material cskdp = new Material();
    public static Material gundam = new Material();

    public static MaterialCategory catCard = new MaterialCategory("魔法卡");
    public static MaterialCategory cardNature = new MaterialCategory(catCard, "自然卡");
    public static MaterialCategory cardBeast = new MaterialCategory(catCard, "怪兽卡");
    public static MaterialCategory cardEmotion = new MaterialCategory(catCard, "心灵卡");
    public static MaterialCategory emotSad = new MaterialCategory(cardEmotion, "悲痛至死卡");
    public static MaterialCategory emotLone = new MaterialCategory(cardEmotion, "孤独至死卡");
    public static MaterialCategory emotBlue = new MaterialCategory(cardEmotion, "绝望至死卡");

    public static StockOrder takeInOrder1;
    public static StockOrder takeOutOrder1;
    public static StockOrder planOrder1;

    static {
        stockPolicy.setLabel("库存审核策略");
        stockPolicy.setDescription("测试用的库存审核策略。");
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_M, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MB, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MBC, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MBL, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MBLC, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MC, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.INIT, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.TAKE_IN, SEMVerifyPolicySamples.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.TAKE_OUT, SEMVerifyPolicySamples.robotList);

        tokyoWarehouse.setName("TK-01");
        tokyoWarehouse.setLabel("东京一号仓库");
        tokyoWarehouse.setAddress("東京都中央区八重洲一丁目5番3-103号");
        tokyoWarehouse.setPhone("911");
        tokyoWarehouse.setManager(SEMPeopleSamples.jackPerson);

        area1.setAddress("5番-11区");
        area1.setWarehouse(tokyoWarehouse);

        area2.setAddress("5番-3区");
        area2.setWarehouse(tokyoWarehouse);

        hokaidou.setAddress("5番3号");
        hokaidou.setWarehouse(tokyoWarehouse);
        hokaidou.setParent(area1);

        rawWarehouse.setName("1#原材料仓库");
        rawWarehouse.setLabel("1#原材料仓库");

        loc1.setAddress("第一排");
        loc1.setWarehouse(rawWarehouse);

        loc11.setAddress("第一号");
        loc11.setWarehouse(rawWarehouse);
        loc11.setParent(loc1);

        MaterialPreferredLocation cskdpPL1 = new MaterialPreferredLocation();
        cskdpPL1.setMaterial(cskdp);
        cskdpPL1.setLocation(hokaidou);
        cskdpPL1.setDescription("...那啥?");

        MaterialPreferredLocation cskdpPL2 = new MaterialPreferredLocation();
        cskdpPL2.setMaterial(cskdp);
        cskdpPL2.setLocation(area1);
        cskdpPL2.setDescription("R2");

        MaterialWarehouseOption cskdpOption = new MaterialWarehouseOption();
        cskdpOption.setMaterial(cskdp);
        cskdpOption.setSafetyStock(new BigDecimal(6));
        cskdpOption.setWarehouse(tokyoWarehouse);

        cskdp.setLabel("超时空大炮");
        cskdp.setUnitHint("体积");
        cskdp.setUnit(Unit.CUBIC_METER);
        cskdp.setBarCode("1693612370098");
        cskdp.setSerial("..M1");
        cskdp.setCategory(catCard);
        cskdp.setPreferredLocations(Arrays.asList(cskdpPL1, cskdpPL2));
        cskdp.setOptions(Arrays.asList(cskdpOption));

        gundam.setLabel("机动战士高达");
        gundam.setUnit(Unit.CUBIC_METER);
        gundam.setBarCode("LSLT-02");
        gundam.setSerial("..M2");
        gundam.setCategory(cardNature);
        gundam.setUnitHint("体积");

        MaterialAttribute cskdpDistAttr = new MaterialAttribute(cskdp, "炮程", "120km");
        MaterialAttribute cskdpRefmaAttr = new MaterialAttribute(cskdp, "推荐手办", "ABD-432");
        cskdp.setAttributes(Arrays.asList(cskdpDistAttr, cskdpRefmaAttr));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.DAY_OF_MONTH, 19);

        MaterialPrice price_chskdp1 = new MaterialPrice();
        price_chskdp1.setMaterial(cskdp);
        price_chskdp1.setDate(cal.getTime());
        price_chskdp1.setPrice(1212.1212);
        MaterialPrice price_chskdp2 = new MaterialPrice();
        price_chskdp2.setMaterial(cskdp);
        price_chskdp2.setPrice(3434.34);
        cskdp.setPrices(Arrays.asList(price_chskdp1, price_chskdp2));

        MaterialPrice price_gundam = new MaterialPrice();
        price_gundam.setMaterial(gundam);
        price_gundam.setPrice(5656.56);
        gundam.setPrices(Arrays.asList(price_gundam));

        cskdpDistAttr.setMaterial(cskdp);
        cskdpDistAttr.setName("主要材料");
        cskdpDistAttr.setValue("Al合金");

        cskdpRefmaAttr.setMaterial(cskdp);
        cskdpRefmaAttr.setName("其他材料");
        cskdpRefmaAttr.setValue("凝气胶,巴基球等");

        takeInOrder1 = new StockOrder();
        {
            takeInOrder1.setSubject(StockOrderSubject.TAKE_IN);
            takeInOrder1.setSerial("..TK_I:1");
            takeInOrder1.setWarehouse(rawWarehouse);
            takeInOrder1.setBeginTime(DateSamples.D_2010_07_20);

            StockOrderItem item1 = new StockOrderItem(takeInOrder1);
            item1.setMaterial(gundam);
            item1.setBatch("C01");
            item1.setQuantity(8);
            item1.setPrice(1200.0);
            takeInOrder1.addItem(item1);

            StockOrderItem item2 = new StockOrderItem(takeInOrder1);
            item2.setMaterial(cskdp);
            item2.setQuantity(85);
            item2.setPrice(755.0);
            takeInOrder1.addItem(item2);
        }

        takeOutOrder1 = new StockOrder();
        {
            takeOutOrder1.setSubject(StockOrderSubject.TAKE_OUT);
            takeOutOrder1.setSerial("..TK_O:1");
            takeOutOrder1.setWarehouse(rawWarehouse);
            takeOutOrder1.setBeginTime(DateSamples.D_2010_07_20);

            StockOrderItem item1 = new StockOrderItem(takeOutOrder1);
            item1.setMaterial(cskdp);
            item1.setQuantity(-30);
            item1.setPrice(755.0);
            takeOutOrder1.addItem(item1);
        }

        planOrder1 = new StockOrder();
        {
            planOrder1.setSubject(StockOrderSubject.PLAN_OUT);
            planOrder1.setSerial("..PLAN:1");
            planOrder1.setWarehouse(rawWarehouse);
            takeOutOrder1.setBeginTime(DateSamples.D_2010_07_30);

            StockOrderItem item1 = new StockOrderItem(planOrder1);
            item1.setMaterial(gundam);
            item1.setBatch("C01");
            item1.setQuantity(-3);
            item1.setPrice(1200.0);
            planOrder1.addItem(item1);

            StockOrderItem item2 = new StockOrderItem(planOrder1);
            item2.setMaterial(cskdp);
            item2.setQuantity(-15);
            item2.setPrice(755.0);
            planOrder1.addItem(item2);
        }
    }

    @Override
    protected void preamble() {
        add(stockPolicy);

        add(tokyoWarehouse);
        addBulk(area1, area2, hokaidou);

        add(rawWarehouse);
        addBulk(loc1, loc11);

        addBulk(catCard, cardNature, cardBeast, cardEmotion, emotSad, emotLone, emotBlue);
        addBulk(cskdp, gundam);

        add(takeInOrder1);
        add(takeOutOrder1);
        add(planOrder1);
    }

}
