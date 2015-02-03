package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;

import com.tinylily.model.base.CoMomentIntervalCriteria;
import com.tinylily.model.sea.QVariantMap;

public class AccountingEntryCriteria
        extends CoMomentIntervalCriteria {

    public Integer accountId;
    public Integer side;
    public DoubleRange valueRange;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
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
        accountId = map.getInt("account", accountId);
        side = map.getInt("side", side);
        valueRange = map.getDoubleRange("values", valueRange);
    }

}
