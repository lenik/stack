package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;
import net.bodz.lily.model.mx.base.CoMessageCriteria;
import net.bodz.lily.model.sea.QVariantMap;

public class TopicCriteria
        extends CoMessageCriteria {

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
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        valueRange = map.getIntRange("values", valueRange);
    }

}
