package com.bee32.sem.world.monetary;

import java.util.Currency;

import javax.free.UnexpectedException;

public abstract class AbstractFxrProvider
        implements IFxrProvider {

    public static final int FXR_BASE_RATE = 0;
    public static final int FXR_BUYING_RATE = 1;
    public static final int FXR_SELLING_RATE = 2;
    public static final int FXR_MIDDLE_RATE = 3;

    public int defaultRateUsage = FXR_MIDDLE_RATE;

    @Override
    public Float getLatestFxr(Currency quoteCurrency)
            throws FxrQueryException {
        FxrTable table = getLatestFxrTable();
        if (table == null)
            return null;

        FxrRecord record = table.getQuote(quoteCurrency);
        float rate;
        switch (defaultRateUsage) {
        case FXR_BASE_RATE:
            rate = record.getBaseRate();
            break;

        case FXR_BUYING_RATE:
            rate = record.getBuyingRate();
            break;

        case FXR_SELLING_RATE:
            rate = record.getSellingRate();
            break;

        case FXR_MIDDLE_RATE:
            rate = record.getMiddleRate();
            break;

        default:
            throw new UnexpectedException();
        }
        return rate;
    }

}
