package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.base.CoMomentIntervalCriteria;
import com.tinylily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.sales.SalesOrderItem
 */
public class SalesOrderItemCriteria
        extends CoMomentIntervalCriteria {

    public Integer salesOrderId;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        salesOrderId = map.getInt("sdoc", salesOrderId);
    }

}
