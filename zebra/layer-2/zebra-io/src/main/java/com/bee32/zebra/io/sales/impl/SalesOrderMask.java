package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.lily.model.mx.base.CoMessageMask;

/**
 * @see com.bee32.zebra.io.sales.SalesOrder
 */
public class SalesOrderMask
        extends CoMessageMask {

    public Integer topicId;

    @Override
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        topicId = map.getInt("topic", topicId);
    }

}
