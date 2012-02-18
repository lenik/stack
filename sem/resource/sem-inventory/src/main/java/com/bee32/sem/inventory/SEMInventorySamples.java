package com.bee32.sem.inventory;

import static com.bee32.sem.inventory.entity.MaterialType.PRODUCT;
import static com.bee32.sem.inventory.entity.MaterialType.RAW;
import static com.bee32.sem.inventory.entity.MaterialType.SEMI;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicy;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.process.verify.VerifyContextAccessor;
import com.bee32.sem.test.DateSamples;
import com.bee32.sem.world.SEMWorldSamples;
import com.bee32.sem.world.thing.Unit;

@ImportSamples({ SEMPeopleSamples.class, SEMWorldSamples.class, SEMVerifyPolicySamples.class })
public class SEMInventorySamples
        extends SampleContribution {

    public static StockOrderVerifyPolicy stockPolicy = new StockOrderVerifyPolicy();

    public static StockWarehouse mainWarehouse = new StockWarehouse();
    public static StockWarehouse rawWarehouse = new StockWarehouse();

    public static StockLocation sl_glass_1 = new StockLocation(rawWarehouse, "防爆玻璃区");
    public static StockLocation sl_glue_1 = new StockLocation(rawWarehouse, "综合胶水区");
    public static StockLocation sl_glue_pp = new StockLocation(rawWarehouse, "等离子胶水预搅拌区", sl_glue_1);
    public static StockLocation sl_handler_1 = new StockLocation(mainWarehouse, "一楼");
    public static StockLocation sl_handler_2 = new StockLocation(mainWarehouse, "地下拿手废料区");
    public static StockLocation sl_handler_KJ = new StockLocation(mainWarehouse, "氪金拿手区", sl_handler_1);
    public static StockLocation sl_handler_F1 = new StockLocation(mainWarehouse, "方程式氪金燃烧室", sl_handler_KJ);
    public static StockLocation sl_dedi_1 = new StockLocation(mainWarehouse, "一楼");
    public static StockLocation sl_light_1 = new StockLocation(mainWarehouse, "二楼");

    public static MaterialCategory cupRoot = new MaterialCategory(PRODUCT, "宇航员的杯具");
    public static MaterialCategory cupSet_dedi = new MaterialCategory(cupRoot, PRODUCT, "客户定制杯具");
    public static MaterialCategory cupSet_light = new MaterialCategory(cupRoot, PRODUCT, "专利无重力杯具");
    public static MaterialCategory cupGlass_AM = new MaterialCategory(cupRoot, RAW, "反物质玻璃");
    public static MaterialCategory cupGlue = new MaterialCategory(cupRoot, RAW, "等离子胶水");
    public static MaterialCategory cupGlue_pp = new MaterialCategory(cupGlue, SEMI, "预搅拌等离子胶水");
    public static MaterialCategory cupHandler = new MaterialCategory(cupRoot, SEMI, "杯具拿手");
    public static MaterialCategory cupHandler_KJ = new MaterialCategory(cupHandler, SEMI, "氪金拿手");
    public static MaterialCategory cupHandler_F1 = new MaterialCategory(cupHandler_KJ, SEMI, "方程式氪金拿手");

    public static Material m_light_A = new Material();
    public static Material m_light_B = new Material();
    public static Material m_glass1 = new Material();
    public static Material m_glue1 = new Material();
    public static Material m_gluepp1 = new Material();
    public static Material m_handlerkj1 = new Material();
    public static Material m_handlerkj2 = new Material();
    public static Material m_handlerf1 = new Material();

    public static StockOrder takeInOrder1;
    public static StockOrder takeOutOrder1;
    public static StockOrder factoryInOrder1;
    public static StockOrder factoryOutOrder1;
    public static StockOrder planOrder1;

    static {
        stockPolicy.setName(".stockvp-1");
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

        mainWarehouse.setName("ASP-S");
        mainWarehouse.setLabel("宇航员杯具综合仓库");
        mainWarehouse.setAddress("浙江省楚门镇城东路11号先锋杯具公司北");
        mainWarehouse.setPhone("911");
        mainWarehouse.setManager(SEMPeopleSamples.jackPerson);

        rawWarehouse.setName("ASP-R");
        rawWarehouse.setLabel("宇航员杯具原材料仓库");
        mainWarehouse.setAddress("浙江省楚门镇城东路11号先锋杯具公司东厂");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        Date date = cal.getTime();

        m_light_A.setSerial("..M1");
        m_light_A.setLabel("无重力A型杯具");
        m_light_A.setModelSpec("ASP-LG-1A");
        m_light_A.setUnit(Unit.PIECE);
        m_light_A.setBarCode("00120001");
        m_light_A.setCategory(cupSet_light);
        m_light_A.addPreferredLocation(sl_light_1);
        m_light_A.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_light_A.setAttribute("容积", "50L");
        m_light_A.addPrice(date, new BigDecimal(16));

        m_light_B.setSerial("..M2");
        m_light_B.setLabel("无重力B型杯具");
        m_light_B.setModelSpec("ASP-LG-1B");
        m_light_B.setUnit(Unit.PIECE);
        m_light_B.setBarCode("00120002");
        m_light_B.setCategory(cupSet_light);
        m_light_B.addPreferredLocation(sl_light_1);
        m_light_B.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_light_B.setAttribute("容积", "100L");
        m_light_B.addPrice(date, new BigDecimal(22));

        m_glass1.setSerial("..M3");
        m_glass1.setLabel("牛顿Z型反物质玻璃");
        m_glass1.setModelSpec("ASP-AG-Z1");
        m_glass1.setUnit(Unit.SQUARE_METER);
        m_glass1.setBarCode("00121201");
        m_glass1.setCategory(cupGlass_AM);
        m_glass1.addPreferredLocation(sl_glass_1);
        m_glass1.getOption(rawWarehouse).setSafetyStock(new BigDecimal(200));

        m_glue1.setSerial("..M4");
        m_glue1.setLabel("青岛离子胶");
        m_glue1.setModelSpec("ASP-GL-QingDao");
        m_glue1.setUnit(Unit.KILOGRAM);
        m_glue1.setBarCode("00124001");
        m_glue1.setCategory(cupGlue);
        m_glue1.addPreferredLocation(sl_glue_1);
        m_glue1.getOption(rawWarehouse).setSafetyStock(new BigDecimal(100));
        m_glue1.setAttribute("溶点", "450c");

        m_gluepp1.setSerial("..M5");
        m_gluepp1.setLabel("胶州特种离子胶/预处理");
        m_gluepp1.setModelSpec("ASP-GL-JZT1");
        m_gluepp1.setUnit(Unit.P_TONG);
        m_gluepp1.setBarCode("00124050");
        m_gluepp1.setCategory(cupGlue_pp);
        m_gluepp1.addPreferredLocation(sl_glue_pp);
        m_gluepp1.getOption(rawWarehouse).setSafetyStock(new BigDecimal(10));
        m_gluepp1.setAttribute("溶点", "600c");
        m_gluepp1.addUnitConv(Unit.SQUARE_METER, 10);

        m_handlerkj1.setSerial("..M6");
        m_handlerkj1.setLabel("迪拜产氪金拿手");
        m_handlerkj1.setModelSpec("ASP-HKJ-DB");
        m_handlerkj1.setUnit(Unit.P_ZHI);
        m_handlerkj1.setBarCode("00140612");
        m_handlerkj1.setCategory(cupHandler_KJ);
        m_handlerkj1.addPreferredLocation(sl_handler_KJ);
        m_handlerkj1.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_handlerkj1.setAttribute("光滑度", "12mx");

        m_handlerkj2.setSerial("..M7");
        m_handlerkj2.setLabel("迪拜产特种氪金拿手");
        m_handlerkj2.setModelSpec("ASP-HKJ-DX");
        m_handlerkj2.setUnit(Unit.P_ZHI);
        m_handlerkj2.setBarCode("00140613");
        m_handlerkj2.setCategory(cupHandler_KJ);
        m_handlerkj2.addPreferredLocation(sl_handler_KJ);
        m_handlerkj2.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_handlerkj2.setAttribute("光滑度", "16mx");

        m_handlerf1.setSerial("..M8");
        m_handlerf1.setLabel("我厂生产许冠杰式改良方程式拿手Y型");
        m_handlerf1.setModelSpec("ASP-HF1-XGY");
        m_handlerf1.setUnit(Unit.P_ZHI);
        m_handlerf1.setBarCode("00146021");
        m_handlerf1.setCategory(cupHandler_F1);
        m_handlerf1.addPreferredLocation(sl_handler_F1);
        m_handlerf1.getOption(mainWarehouse).setSafetyStock(new BigDecimal(3));
        m_handlerf1.setAttribute("光滑度", "20mx");
        m_handlerf1.addPrice(date, new BigDecimal(30));

        takeInOrder1 = new StockOrder();
        {
            takeInOrder1.setSubject(StockOrderSubject.TAKE_IN);
            takeInOrder1.setSerial("..TK_I:1");
            takeInOrder1.setWarehouse(rawWarehouse);
            takeInOrder1.setBeginTime(DateSamples.D_2010_07_20);

            takeInOrder1.addItem(m_glass1, "B1", 50.0, 85.0);
            takeInOrder1.addItem(m_glue1, "G01", 30.0, 25.0);
            takeInOrder1.addItem(m_gluepp1, null, 8.0, 15.0);
            takeInOrder1.addItem(m_handlerkj1, "Z", 150, 6.5);
            takeInOrder1.addItem(m_handlerkj2, null, 120, 9.0);
        }

        takeOutOrder1 = new StockOrder();
        {
            takeOutOrder1.setSubject(StockOrderSubject.TAKE_OUT);
            takeOutOrder1.setSerial("..TK_O:1");
            takeOutOrder1.setWarehouse(mainWarehouse);
            takeOutOrder1.setBeginTime(DateSamples.D_2010_07_20);
            takeOutOrder1.addItem(m_light_B, null, -5, 50.0);
        }

        factoryInOrder1 = new StockOrder();
        {
            factoryInOrder1.setSubject(StockOrderSubject.FACTORY_IN);
            factoryInOrder1.setSerial("..FK_I:1");
            factoryInOrder1.setWarehouse(mainWarehouse);
            factoryInOrder1.setBeginTime(DateSamples.D_2010_07_20);
            factoryInOrder1.addItem(m_handlerf1, null, 20.0, 30.0/* ??? */);
        }

        factoryOutOrder1 = new StockOrder();
        {
            factoryOutOrder1.setSubject(StockOrderSubject.FACTORY_OUT);
            factoryOutOrder1.setSerial("..FK_O:1");
            factoryOutOrder1.setWarehouse(mainWarehouse);
            factoryOutOrder1.setBeginTime(DateSamples.D_2010_07_30);
            factoryOutOrder1.addItem(m_handlerf1, null, -5.0, 30.0);
        }

        planOrder1 = new StockOrder();
        {
            planOrder1.setSubject(StockOrderSubject.PLAN_OUT);
            planOrder1.setSerial("..PLAN:1");
            planOrder1.setWarehouse(rawWarehouse);
            planOrder1.setBeginTime(DateSamples.D_2010_07_30);
            planOrder1.addItem(m_glass1, "B1", -3, null);
            planOrder1.addItem(m_glue1, "G01", -5, null);
        }
        VerifyContextAccessor.forceVerified(//
                takeInOrder1, takeOutOrder1, //
                factoryInOrder1, factoryOutOrder1, //
                planOrder1);
    }

    @Override
    protected void preamble() {
        add(stockPolicy);
        addBulk(mainWarehouse, rawWarehouse);
        addBulk(sl_dedi_1, sl_light_1, //
                sl_glass_1, sl_glue_1, sl_glue_pp, //
                sl_handler_1, sl_handler_2, sl_handler_KJ, sl_handler_F1);
        addBulk(cupRoot, //
                cupSet_dedi, cupSet_light, //
                cupGlue, cupGlue_pp, //
                cupGlass_AM, //
                cupHandler, cupHandler_KJ, cupHandler_F1);
        addBulk(m_gluepp1.getUnitConv());
        addBulk(m_light_A, m_light_B, //
                m_glass1, //
                m_glue1, m_gluepp1, //
                m_handlerkj1, m_handlerkj2, //
                m_handlerf1);
        addBulk(takeInOrder1, takeOutOrder1, //
                factoryInOrder1, factoryOutOrder1, //
                planOrder1);
    }

}
