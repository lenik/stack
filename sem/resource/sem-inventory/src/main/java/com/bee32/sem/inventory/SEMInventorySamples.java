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
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.world.thing.SEMWorldThingSamples;
import com.bee32.sem.world.thing.Unit;

@ImportSamples({ SEMPeopleSamples.class, SEMWorldThingSamples.class })
public class SEMInventorySamples
        extends SampleContribution {

    public static StockWarehouse stockWarehouse = new StockWarehouse();
    public static StockLocation area1 = new StockLocation();
    public static StockLocation area2 = new StockLocation();
    public static StockLocation hokaidou = new StockLocation();

    public static Material cskdp = new Material();
    public static Material gundam = new Material();

    public static MaterialCategory catCard = new MaterialCategory("魔法卡");
    public static MaterialCategory cardNature = new MaterialCategory(catCard, "自然卡");
    public static MaterialCategory cardBeast = new MaterialCategory(catCard, "怪兽卡");
    public static MaterialCategory cardEmotion = new MaterialCategory(catCard, "心灵卡");
    public static MaterialCategory emotSad = new MaterialCategory(cardEmotion, "悲痛至死卡");
    public static MaterialCategory emotLone = new MaterialCategory(cardEmotion, "孤独至死卡");
    public static MaterialCategory emotBlue = new MaterialCategory(cardEmotion, "绝望至死卡");

    static {
        stockWarehouse.setName("TK-01");
        stockWarehouse.setLabel("东京一号仓库");
        stockWarehouse.setAddress("東京都中央区八重洲一丁目5番3-103号");
        stockWarehouse.setPhone("911");
        stockWarehouse.setManager(SEMPeopleSamples.jackPerson);

        area1.setAddress("5番-11区");
        area1.setWarehouse(stockWarehouse);

        area2.setAddress("5番-3区");
        area2.setWarehouse(stockWarehouse);

        hokaidou.setAddress("5番3号");
        hokaidou.setWarehouse(stockWarehouse);
        hokaidou.setParent(area1);

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
        cskdpOption.setWarehouse(stockWarehouse);

        cskdp.setName("超时空大炮");
        cskdp.setUnitHint("体积");
        cskdp.setUnit(Unit.CUBIC_METER);
        cskdp.setBarCode("1693612370098");
        cskdp.setSerial("TeST-CSKDP-01");
        cskdp.setCategory(catCard);
        cskdp.setPreferredLocations(Arrays.asList(cskdpPL1, cskdpPL2));
        cskdp.setOptions(Arrays.asList(cskdpOption));

        gundam.setName("机动战士高达");
        gundam.setUnit(Unit.CUBIC_METER);
        gundam.setBarCode("LSLT-02");
        gundam.setSerial("TeST-SHAKANA-11");
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

    }

    @Override
    protected void preamble() {
        add(stockWarehouse);

        addBulk(area1, area2);
        add(hokaidou);

        addBulk(catCard, cardNature, cardBeast, cardEmotion, emotSad, emotLone, emotBlue);

        addBulk(cskdp, gundam);
    }

}
