package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;

import com.tinylily.model.base.CoMomentIntervalCriteria;
import com.tinylily.model.sea.QVariantMap;

public class AccountingEntryCriteria
        extends CoMomentIntervalCriteria {

    Integer side;
    Integer code_;
    DoubleRange valueRange;

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public Integer getAccountCodePrefix() {
        return code_;
    }

    public void setAccountCodePrefix(Integer accountCodePrefix) {
        this.code_ = accountCodePrefix;
    }

    public DoubleRange getValueRange() {
        return valueRange;
    }

    public void setValueRange(DoubleRange valueRange) {
        this.valueRange = valueRange;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        side = map.getInt("side", side);
        code_ = map.getInt("code", code_);
        valueRange = map.getDoubleRange("values", valueRange);
    }

}
