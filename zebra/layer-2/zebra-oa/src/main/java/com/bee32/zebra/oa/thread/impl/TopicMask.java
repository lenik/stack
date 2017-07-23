package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.QVariantMap;
import net.bodz.lily.model.mx.CoMessageMask;

public class TopicMask
        extends CoMessageMask {

    public IntRange valueRange;

    /**
     * 价值
     */
    public IntRange getValueRange() {
        return valueRange;
    }

    public void setValueRange(IntRange valueRange) {
        this.valueRange = valueRange;
    }

    @Override
    public void readObject(IVariantMap<String> _map)
            throws ParseException {
        super.readObject(_map);
        QVariantMap<String> map = QVariantMap.from(_map);
        valueRange = map.getIntRange("values", valueRange);
    }

}
