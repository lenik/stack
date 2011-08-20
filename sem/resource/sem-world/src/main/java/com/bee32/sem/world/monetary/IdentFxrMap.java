package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;

class IdentFxrMap
        implements IFxrMap {

    @Override
    public Currency getUnit() {
        return null;
    }

    @Override
    public FxrUsage getUsage() {
        return null;
    }

    @Override
    public void plot(Date date, float rate) {
    }

    @Override
    public double eval(Date date) {
        return 1.0;
    }

    public static final IdentFxrMap INSTANCE = new IdentFxrMap();

}
