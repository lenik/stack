package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.lily.model.mx.base.CoMessageCriteria;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.stock.StockEvent
 */
public class StockEventCriteria
        extends CoMessageCriteria {

    public Long topicId;
    public Integer orgId;
    public Integer orgUnitId;
    public Integer personId;
    public DoubleRange totalRange;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        topicId = map.getLong("topic", topicId);
        orgId = map.getInt("org", orgId);
        orgUnitId = map.getInt("ou", orgUnitId);
        personId = map.getInt("person", personId);
        totalRange = map.getDoubleRange("totals", totalRange);
    }

}
