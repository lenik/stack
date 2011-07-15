package com.bee32.sem.world.thing;

import com.bee32.plover.orm.util.EntitySamplesContribution;

public class SEMWorldSamples
        extends EntitySamplesContribution {

    public static UnitConv baseConv = new UnitConv();

    static {
        baseConv.setName("baseWeightRatio");
        baseConv.setDescription("基本重量换算");
        baseConv.setParent(null);
        baseConv.setFrom(Unit.KILOGRAM);
        baseConv.ratioMap.put(Unit.GRAM, 1000.0);
        baseConv.ratioMap.put(Unit.MILLIGRAM, 1000000.0);
    }

    @Override
    protected void preamble() {
        addNormalSample(Unit.CENTIMETER);
        addNormalSample(Unit.CUBIC_METER);
        addNormalSample(Unit.DECIMETER);
        addNormalSample(Unit.GRAM);
        addNormalSample(Unit.KILOGRAM);
        addNormalSample(Unit.KILOMETER);
        addNormalSample(Unit.LITER);
        addNormalSample(Unit.METER);
        addNormalSample(Unit.MILLIGRAM);
        addNormalSample(Unit.MILLILITER);
        addNormalSample(Unit.NEWTON);
        addNormalSample(Unit.SQUARE_CENTIMETER);
        addNormalSample(Unit.SQUARE_KILOMETER);
        addNormalSample(Unit.SQUARE_METER);

        addNormalSample(baseConv);
    }

}
