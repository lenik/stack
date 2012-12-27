package com.bee32.sem.material;

import static com.bee32.sem.material.entity.MaterialType.PRODUCT;
import static com.bee32.sem.material.entity.MaterialType.RAW;
import static com.bee32.sem.material.entity.MaterialType.SEMI;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.plover.orm.sample.SampleList;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.material.entity.StockLocation;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.Units;

public class SEMMaterialSamples
        extends NormalSamples {

    public final UnitConv m_gluepp1_uc = new UnitConv();

    public final StockWarehouse mainWarehouse = new StockWarehouse();
    public final StockWarehouse rawWarehouse = new StockWarehouse();

    public final StockLocation sl_glass_1 = new StockLocation(rawWarehouse, "防爆玻璃区");
    public final StockLocation sl_glue_1 = new StockLocation(rawWarehouse, "综合胶水区");
    public final StockLocation sl_glue_pp = new StockLocation(rawWarehouse, "等离子胶水预搅拌区", sl_glue_1);
    public final StockLocation sl_handler_1 = new StockLocation(mainWarehouse, "一楼");
    public final StockLocation sl_handler_2 = new StockLocation(mainWarehouse, "地下拿手废料区");
    public final StockLocation sl_handler_KJ = new StockLocation(mainWarehouse, "氪金拿手区", sl_handler_1);
    public final StockLocation sl_handler_F1 = new StockLocation(mainWarehouse, "方程式氪金燃烧室", sl_handler_KJ);
    public final StockLocation sl_dedi_1 = new StockLocation(mainWarehouse, "一楼");
    public final StockLocation sl_light_1 = new StockLocation(mainWarehouse, "二楼");

    public final MaterialCategory cupRoot = new MaterialCategory(PRODUCT, PREFIX + "宇航员的杯具");
    public final MaterialCategory cupSet_dedi = new MaterialCategory(cupRoot, PRODUCT, "客户定制杯具");
    public final MaterialCategory cupSet_light = new MaterialCategory(cupRoot, PRODUCT, "专利无重力杯具");
    public final MaterialCategory cupGlass_AM = new MaterialCategory(cupRoot, RAW, "反物质玻璃");
    public final MaterialCategory cupGlue = new MaterialCategory(cupRoot, RAW, "等离子胶水");
    public final MaterialCategory cupGlue_pp = new MaterialCategory(cupGlue, SEMI, "预搅拌等离子胶水");
    public final MaterialCategory cupHandler = new MaterialCategory(cupRoot, SEMI, "杯具拿手");
    public final MaterialCategory cupHandler_KJ = new MaterialCategory(cupHandler, SEMI, "氪金拿手");
    public final MaterialCategory cupHandler_F1 = new MaterialCategory(cupHandler_KJ, SEMI, "方程式氪金拿手");

    public final Material m_light_A = new Material();
    public final Material m_light_B = new Material();
    public final Material m_glass1 = new Material();
    public final Material m_glue1 = new Material();
    public final Material m_gluepp1 = new Material();
    public final Material m_handlerkj1 = new Material();
    public final Material m_handlerkj2 = new Material();
    public final Material m_handlerf1 = new Material();

    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);
    Units units = predefined(Units.class);

    @Override
    protected void wireUp() {
        mainWarehouse.setName("ASP-S");
        mainWarehouse.setLabel(PREFIX + "宇航员杯具综合仓库");
        mainWarehouse.setAddress("浙江省楚门镇城东路11号先锋杯具公司北");
        mainWarehouse.setPhone("911");
        mainWarehouse.setManager(people.jackPerson);

        rawWarehouse.setName("ASP-R");
        rawWarehouse.setLabel(PREFIX + "宇航员杯具原材料仓库");
        mainWarehouse.setAddress("浙江省楚门镇城东路11号先锋杯具公司东厂");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        Date date = cal.getTime();

        m_light_A.setLabel(PREFIX + "无重力A型杯具");
        m_light_A.setModelSpec("ASP-LG-1A");
        m_light_A.setUnit(units.PIECE);
        m_light_A.setBarCode("00120001");
        m_light_A.setCategory(cupSet_light);
        m_light_A.addPreferredLocation(sl_light_1);
        m_light_A.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_light_A.setAttribute("容积", "50L");
        m_light_A.addPrice(date, new BigDecimal(16));

        m_light_B.setLabel(PREFIX + "无重力B型杯具");
        m_light_B.setModelSpec("ASP-LG-1B");
        m_light_B.setUnit(units.PIECE);
        m_light_B.setBarCode("00120002");
        m_light_B.setCategory(cupSet_light);
        m_light_B.addPreferredLocation(sl_light_1);
        m_light_B.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_light_B.setAttribute("容积", "100L");
        m_light_B.addPrice(date, new BigDecimal(22));

        m_glass1.setLabel(PREFIX + "牛顿Z型反物质玻璃");
        m_glass1.setModelSpec("ASP-AG-Z1");
        m_glass1.setUnit(units.SQUARE_METER);
        m_glass1.setBarCode("00121201");
        m_glass1.setCategory(cupGlass_AM);
        m_glass1.addPreferredLocation(sl_glass_1);
        m_glass1.getOption(rawWarehouse).setSafetyStock(new BigDecimal(200));

        m_glue1.setLabel(PREFIX + "青岛离子胶");
        m_glue1.setModelSpec("ASP-GL-QingDao");
        m_glue1.setUnit(units.KILOGRAM);
        m_glue1.setBarCode("00124001");
        m_glue1.setCategory(cupGlue);
        m_glue1.addPreferredLocation(sl_glue_1);
        m_glue1.getOption(rawWarehouse).setSafetyStock(new BigDecimal(100));
        m_glue1.setAttribute("溶点", "450c");

        m_gluepp1.setLabel(PREFIX + "胶州特种离子胶/预处理");
        m_gluepp1.setModelSpec("ASP-GL-JZT1");
        m_gluepp1.setUnit(units.P_TONG);
        m_gluepp1.setBarCode("00124050");
        m_gluepp1.setCategory(cupGlue_pp);
        m_gluepp1.addPreferredLocation(sl_glue_pp);
        m_gluepp1.getOption(rawWarehouse).setSafetyStock(new BigDecimal(10));
        m_gluepp1.setAttribute("溶点", "600c");
        m_gluepp1.setUnitConv(m_gluepp1_uc);
        m_gluepp1_uc.setLabel(PREFIX + "胶州特种离子胶/预处理");
        m_gluepp1_uc.setUnit(m_gluepp1.getUnit());
        m_gluepp1_uc.setScale(units.SQUARE_METER, 10.0);

        m_handlerkj1.setLabel(PREFIX + "迪拜产氪金拿手");
        m_handlerkj1.setModelSpec("ASP-HKJ-DB");
        m_handlerkj1.setUnit(units.P_ZHI);
        m_handlerkj1.setBarCode("00140612");
        m_handlerkj1.setCategory(cupHandler_KJ);
        m_handlerkj1.addPreferredLocation(sl_handler_KJ);
        m_handlerkj1.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_handlerkj1.setAttribute("光滑度", "12mx");

        m_handlerkj2.setLabel(PREFIX + "迪拜产特种氪金拿手");
        m_handlerkj2.setModelSpec("ASP-HKJ-DX");
        m_handlerkj2.setUnit(units.P_ZHI);
        m_handlerkj2.setBarCode("00140613");
        m_handlerkj2.setCategory(cupHandler_KJ);
        m_handlerkj2.addPreferredLocation(sl_handler_KJ);
        m_handlerkj2.getOption(mainWarehouse).setSafetyStock(new BigDecimal(10));
        m_handlerkj2.setAttribute("光滑度", "16mx");

        m_handlerf1.setLabel(PREFIX + "我厂生产许冠杰式改良方程式拿手Y型");
        m_handlerf1.setModelSpec("ASP-HF1-XGY");
        m_handlerf1.setUnit(units.P_ZHI);
        m_handlerf1.setBarCode("00146021");
        m_handlerf1.setCategory(cupHandler_F1);
        m_handlerf1.addPreferredLocation(sl_handler_F1);
        m_handlerf1.getOption(mainWarehouse).setSafetyStock(new BigDecimal(3));
        m_handlerf1.setAttribute("光滑度", "20mx");
        m_handlerf1.addPrice(date, new BigDecimal(30));

    }

    @Deprecated
    protected void _getSamples(SampleList samples) {
        samples.addBatch(mainWarehouse, rawWarehouse);
        samples.addBatch(sl_dedi_1, sl_light_1, //
                sl_glass_1, sl_glue_1, sl_glue_pp, //
                sl_handler_1, sl_handler_2, sl_handler_KJ, sl_handler_F1);
        samples.addBatch(cupRoot, //
                cupSet_dedi, cupSet_light, //
                cupGlue, cupGlue_pp, //
                cupGlass_AM, //
                cupHandler, cupHandler_KJ, cupHandler_F1);
        samples.addBatch(m_light_A, m_light_B, //
                m_glass1, //
                m_glue1, m_gluepp1, //
                m_handlerkj1, m_handlerkj2, //
                m_handlerf1);
    }

}
