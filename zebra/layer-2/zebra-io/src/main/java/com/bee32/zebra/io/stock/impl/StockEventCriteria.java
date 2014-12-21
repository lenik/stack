package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.stock.StockEvent
 */
public class StockEventCriteria
        extends CoEntityCriteria {

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
    }

}
