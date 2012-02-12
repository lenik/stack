package com.bee32.sem.bom;

import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;
import javax.free.UnexpectedException;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.inventory.SEMInventorySamples;

@ImportSamples({ SEMInventorySamples.class })
public class SEMBomSamples
        extends SampleContribution {

    public static Part p_light_A = new Part();
    public static Part p_light_B = new Part();
    public static Part p_handler_kj1 = new Part();
    public static Part p_handler_kj2 = new Part();
    public static Part p_handlerf1 = new Part();

    static {
        p_light_A.setTarget(SEMInventorySamples.m_light_A);
        p_light_B.setTarget(SEMInventorySamples.m_light_B);
        p_handler_kj1.setTarget(SEMInventorySamples.m_handlerkj1);
        p_handler_kj2.setTarget(SEMInventorySamples.m_handlerkj2);
        p_handlerf1.setTarget(SEMInventorySamples.m_handlerf1);

        p_light_A.addChild(SEMInventorySamples.m_glass1, 0.2);
        p_light_A.addChild(SEMInventorySamples.m_glue1, 0.1);
        p_light_A.addChild(SEMInventorySamples.m_gluepp1, 0.05);
        p_light_A.addChild(p_handler_kj1, 1);

        p_light_B.addChild(SEMInventorySamples.m_glass1, 0.25);
        p_light_B.addChild(SEMInventorySamples.m_glue1, 0.1);
        p_light_B.addChild(SEMInventorySamples.m_gluepp1, 0.1);
        p_light_B.addChild(p_handlerf1, 1);

        p_handler_kj1.addChild(SEMInventorySamples.m_handlerkj1, 1);
        p_handler_kj2.addChild(SEMInventorySamples.m_handlerkj1, 1);

        p_handlerf1.addChild(SEMInventorySamples.m_handlerkj2, 1);
        p_handlerf1.addChild(SEMInventorySamples.m_glass1, 0.03);
        p_handlerf1.addChild(SEMInventorySamples.m_glue1, 0.01);
    }

    static Date parseDate(String str) {
        try {
            return Dates.YYYY_MM_DD.parse(str);
        } catch (ParseException e) {
            throw new UnexpectedException(e);
        }
    }

    @Override
    protected void preamble() {
        addBulk(p_light_A, p_light_B, //
                p_handlerf1, p_handler_kj1, p_handler_kj2);
    }

}
