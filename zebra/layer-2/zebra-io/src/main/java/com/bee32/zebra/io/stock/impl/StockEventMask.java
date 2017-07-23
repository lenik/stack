package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.QVariantMap;
import net.bodz.lily.model.mx.CoMessageMask;

/**
 * @see com.bee32.zebra.io.stock.StockEvent
 */
public class StockEventMask
        extends CoMessageMask {

    public Long topicId;
    public Integer orgId;
    public Integer orgUnitId;
    public Integer personId;
    public DoubleRange totalRange;

    @Override
    public void readObject(IVariantMap<String> _map)
            throws ParseException {
        super.readObject(_map);
        QVariantMap<String> map = QVariantMap.from(_map);
        topicId = map.getLong("topic", topicId);
        orgId = map.getInt("org", orgId);
        orgUnitId = map.getInt("ou", orgUnitId);
        personId = map.getInt("person", personId);
        totalRange = map.getDoubleRange("totals", totalRange);
    }

}
