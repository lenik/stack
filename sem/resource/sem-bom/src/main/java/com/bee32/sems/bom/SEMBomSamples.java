package com.bee32.sems.bom;

import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;
import javax.free.UnexpectedException;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sems.bom.entity.Part;
import com.bee32.sems.bom.entity.PartItem;

@ImportSamples({ SEMInventorySamples.class })
public class SEMBomSamples
        extends SampleContribution {

    public static Part part = new Part();

    static {
        part.setTarget(SEMInventorySamples.gundam);
        part.setValidDateFrom(parseDate("2010-03-05"));
        part.setValidDateTo(parseDate("2020-03-04"));
        part.setCreator(SEMPeopleSamples.jack);

        PartItem item = new PartItem();
        item.setParent(part);
        item.setMaterial(SEMInventorySamples.cskdp);
        item.setQuantity(20L);
        item.setValid(true);
        item.setValidDateFrom(parseDate("2010-03-05"));
        item.setValidDateTo(parseDate("2503-03-04"));
        part.addChild(item);
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
        add(part);
    }

}
