package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.base.CoMomentIntervalMask;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.sales.SalesOrderItem
 */
public class SalesOrderItemMask
        extends CoMomentIntervalMask {

    public Integer salesOrderId;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        salesOrderId = map.getInt("doc", salesOrderId);
    }

}
