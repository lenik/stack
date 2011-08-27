package com.bee32.plover.ox1.config;

import java.math.MathContext;

public interface DecimalConfig {

    int QTY_ITEM_PRECISION = 16;
    int QTY_ITEM_SCALE = 4;
    MathContext QTY_ITEM_CONTEXT = new MathContext(QTY_ITEM_PRECISION);

    int QTY_TOTAL_PRECISION = 20;
    int QTY_TOTAL_SCALE = 4;
    MathContext QTY_TOTAL_CONTEXT = new MathContext(QTY_TOTAL_PRECISION);

    int MONEY_ITEM_PRECISION = 16;
    int MONEY_ITEM_SCALE = 4;
    MathContext MONEY_ITEM_CONTEXT = new MathContext(MONEY_ITEM_PRECISION);

    int MONEY_TOTAL_PRECISION = 20;
    int MONEY_TOTAL_SCALE = 4;
    MathContext MONEY_TOTAL_CONTEXT = new MathContext(MONEY_TOTAL_PRECISION);

}
