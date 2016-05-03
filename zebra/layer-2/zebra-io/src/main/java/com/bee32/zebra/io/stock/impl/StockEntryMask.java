package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;

import com.bee32.zebra.tk.sea.MyMask;

/**
 * @see com.bee32.zebra.io.stock.StockEntry
 */
public class StockEntryMask
        extends MyMask {

    @Override
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
    }

}
