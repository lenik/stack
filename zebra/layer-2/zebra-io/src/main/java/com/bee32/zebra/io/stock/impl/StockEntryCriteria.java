package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.err.ParseException;

import com.bee32.zebra.tk.sea.MyCriteria;
import com.tinylily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.stock.StockEntry
 */
public class StockEntryCriteria
        extends MyCriteria {

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
    }

}
