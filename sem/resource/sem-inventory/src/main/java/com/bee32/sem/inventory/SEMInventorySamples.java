package com.bee32.sem.inventory;


import static com.bee32.sem.inventory.entity.StockOrderSubject.FACTORY_IN;
import static com.bee32.sem.inventory.entity.StockOrderSubject.FACTORY_OUT;
import static com.bee32.sem.inventory.entity.StockOrderSubject.PLAN_OUT;
import static com.bee32.sem.inventory.entity.StockOrderSubject.TAKE_IN;
import static com.bee32.sem.inventory.entity.StockOrderSubject.TAKE_OUT;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.plover.orm.sample.SampleList;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicy;
import com.bee32.sem.material.SEMMaterialSamples;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.process.verify.VerifyContextAccessor;
import com.bee32.sem.test.DateSamples;
import com.bee32.sem.world.thing.Units;

public class SEMInventorySamples
        extends NormalSamples {

    public final StockOrderVerifyPolicy stockPolicy = new StockOrderVerifyPolicy();

    public final StockOrder cupx_I_1 = new StockOrder(TAKE_IN);
    public final StockOrder lightB_o_1 = new StockOrder(TAKE_OUT);
    public final StockOrder handlerf1_fi = new StockOrder(FACTORY_IN);
    public final StockOrder handlerf1_fo = new StockOrder(FACTORY_OUT);
    public final StockOrder cupx_plan_1 = new StockOrder(PLAN_OUT);

    SEMVerifyPolicySamples verifyPolicies = predefined(SEMVerifyPolicySamples.class);
    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);
    Units units = predefined(Units.class);
    SEMMaterialSamples materials = predefined(SEMMaterialSamples.class);

    @Override
    protected void wireUp() {
        stockPolicy.setLabel(PREFIX + "库存审核策略");
        stockPolicy.setDescription("测试用的库存审核策略。");
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_M, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MB, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MBC, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MBL, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MBLC, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.PACK_MC, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.INIT, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.TAKE_IN, verifyPolicies.robotList);
        stockPolicy.setSubjectPolicy(StockOrderSubject.TAKE_OUT, verifyPolicies.robotList);



        cupx_I_1.setWarehouse(materials.rawWarehouse);
        cupx_I_1.setBeginTime(DateSamples.D_2010_07_20);
        cupx_I_1.addItem(materials.m_glass1, "B1", 50.0, 85.0);
        cupx_I_1.addItem(materials.m_glue1, "G01", 30.0, 25.0);
        cupx_I_1.addItem(materials.m_gluepp1, null, 8.0, 15.0);
        cupx_I_1.addItem(materials.m_handlerkj1, "Z", 150, 6.5);
        cupx_I_1.addItem(materials.m_handlerkj2, null, 120, 9.0);

        lightB_o_1.setWarehouse(materials.mainWarehouse);
        lightB_o_1.setBeginTime(DateSamples.D_2010_07_20);
        lightB_o_1.addItem(materials.m_light_B, null, -5, 50.0);

        handlerf1_fi.setWarehouse(materials.mainWarehouse);
        handlerf1_fi.setBeginTime(DateSamples.D_2010_07_20);
        handlerf1_fi.addItem(materials.m_handlerf1, null, 20.0, 30.0/* ??? */);

        handlerf1_fo.setWarehouse(materials.mainWarehouse);
        handlerf1_fo.setBeginTime(DateSamples.D_2010_07_30);
        handlerf1_fo.addItem(materials.m_handlerf1, null, -5.0, 30.0);

        cupx_plan_1.setWarehouse(materials.rawWarehouse);
        cupx_plan_1.setBeginTime(DateSamples.D_2010_07_30);
        cupx_plan_1.addItem(materials.m_glass1, "B1", -3, null);
        cupx_plan_1.addItem(materials.m_glue1, "G01", -5, null);

        VerifyContextAccessor.forceVerified(//
                cupx_I_1, lightB_o_1, //
                handlerf1_fi, handlerf1_fo, //
                cupx_plan_1);
    }

    @Deprecated
    protected void _getSamples(SampleList samples) {
        samples.addBatch(stockPolicy);
        samples.addBatch(cupx_I_1, lightB_o_1, //
                handlerf1_fi, handlerf1_fo, //
                cupx_plan_1);
    }

    @Override
    public void beginLoad() {
    }

}
