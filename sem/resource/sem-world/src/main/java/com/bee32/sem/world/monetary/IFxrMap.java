package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;

public interface IFxrMap {

    IdentFxrMap IDENT = IdentFxrMap.INSTANCE;

    Currency getUnit();

    FxrUsage getUsage();

    // void lazyLoad(Date from, Date to);

    void plot(Date date, float rate);

    double eval(Date date);

}
