package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.lily.model.base.CoMomentIntervalMask;

/**
 * @see com.bee32.zebra.io.sales.SalesOrderItem
 */
public class SalesOrderItemMask
        extends CoMomentIntervalMask {

    public Integer salesOrderId;

    @Override
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        salesOrderId = map.getInt("doc", salesOrderId);
    }

}
