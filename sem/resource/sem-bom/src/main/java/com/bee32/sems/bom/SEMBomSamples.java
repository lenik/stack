package com.bee32.sems.bom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zhtml.Q;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.inventory.SEMInventorySamples;
import com.bee32.sems.material.SEMMaterialSamples;
import com.bee32.sems.org.SEMOrgSamples;

@ImportSamples({ SEMInventorySamples.class })
public class SEMBomSamples
        extends EntitySamplesContribution {

    public static Product product = new Product();
    public static Component component = new Component();





    static {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        product.setMaterial(SEMMaterialSamples.goldBang);
        product.setCreator(SEMOrgSamples.jack);
        product.setDate(new Date());
        try {
            product.setValidDateFrom(sdf.parse("2010-03-05"));
            product.setValidDateTo(sdf.parse("2503-03-04"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        component.setProduct(product);
        component.setMaterial(SEMMaterialSamples.moonKnife);
        component.setQuantity(20L);
        component.setValid(true);
        component.setDesc("组成组成组成组成");
        try {
            component.setValidDateFrom(sdf.parse("2010-03-05"));
            component.setValidDateTo(sdf.parse("2503-03-04"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void preamble() {
        addNormalSample(product);
        addNormalSample(component);
    }

    @Override
    public void beginLoad() {
        System.err.println("Begin to load bom samples");
    }

}
