package com.bee32.sem.make;

import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;
import javax.free.UnexpectedException;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.make.entity.MakeStepName;
import com.bee32.sem.make.entity.Part;

public class SEMMakeSamples
        extends NormalSamples {

    public final Part p_light_A = new Part();
    public final Part p_light_B = new Part();
    public final Part p_handler_kj1 = new Part();
    public final Part p_handler_kj2 = new Part();
    public final Part p_handlerf1 = new Part();

    public final MakeStepName stepName1 = new MakeStepName();
    public final MakeStepName stepName2 = new MakeStepName();


    SEMInventorySamples inventories = predefined(SEMInventorySamples.class);

    @Override
    protected void wireUp() {
        p_light_A.setTarget(inventories.m_light_A);
        p_light_B.setTarget(inventories.m_light_B);
        p_handler_kj1.setTarget(inventories.m_handlerkj1);
        p_handler_kj2.setTarget(inventories.m_handlerkj2);
        p_handlerf1.setTarget(inventories.m_handlerf1);

        p_light_A.addChild(inventories.m_glass1, 0.2);
        p_light_A.addChild(inventories.m_glue1, 0.1);
        p_light_A.addChild(inventories.m_gluepp1, 0.05);
        p_light_A.addChild(p_handler_kj1, 1);

        p_light_B.addChild(inventories.m_glass1, 0.25);
        p_light_B.addChild(inventories.m_glue1, 0.1);
        p_light_B.addChild(inventories.m_gluepp1, 0.1);
        p_light_B.addChild(p_handlerf1, 1);

        p_handler_kj1.addChild(inventories.m_handlerkj1, 1);
        p_handler_kj2.addChild(inventories.m_handlerkj1, 1);

        p_handlerf1.addChild(inventories.m_handlerkj2, 1);
        p_handlerf1.addChild(inventories.m_glass1, 0.03);
        p_handlerf1.addChild(inventories.m_glue1, 0.01);

        stepName1.setLabel("铣");
        stepName2.setLabel("弧焊");
    }

    static Date parseDate(String str) {
        try {
            return Dates.YYYY_MM_DD.parse(str);
        } catch (ParseException e) {
            throw new UnexpectedException(e);
        }
    }

}
