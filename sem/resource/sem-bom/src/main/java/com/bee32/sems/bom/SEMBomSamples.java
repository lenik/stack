package com.bee32.sems.bom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.free.UnexpectedException;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sems.bom.entity.Component;

@ImportSamples({ SEMInventorySamples.class })
public class SEMBomSamples
        extends SampleContribution {

    public static Component product = new Component();
    public static Component component = new Component();

    static {
        product.setMaterial(SEMInventorySamples.gundam);
        product.setValidDateFrom(parseDate("2010-03-05"));
        product.setValidDateTo(parseDate("2503-03-04"));

        component.setDescription("组成组成组成组成");
        component.setParent(product);
        component.setMaterial(SEMInventorySamples.gundam);
        component.setQuantity(20L);
        component.setValid(true);
        component.setValidDateFrom(parseDate("2010-03-05"));
        component.setValidDateTo(parseDate("2503-03-04"));
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    static Date parseDate(String str) {
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            throw new UnexpectedException(e);
        }
    }

    @Override
    protected void preamble() {
        add(product);
        add(component);
    }

}
