package com.bee32.plover.orm.ext.config;

import java.math.MathContext;

public interface DecimalConfig {

    int QTY_ITEM_PRECISION = 16;
    int QTY_ITEM_SCALE = 4;
    MathContext QTY_ITEM_CONTEXT = new MathContext(QTY_ITEM_PRECISION);

    int MONEY_ITEM_PRECISION = 16;
    int MONEY_ITEM_SCALE = 4;
    MathContext MONEY_ITEM_CONTEXT = new MathContext(MONEY_ITEM_PRECISION);

}
