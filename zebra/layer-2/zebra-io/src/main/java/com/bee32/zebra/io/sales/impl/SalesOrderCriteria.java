package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.mx.base.CoMessageCriteria;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.sales.SalesOrder
 */
public class SalesOrderCriteria
        extends CoMessageCriteria {

    public Integer topicId;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        topicId = map.getInt("topic", topicId);
    }

}
