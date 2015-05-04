package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.lily.model.base.CoMomentIntervalCriteria;
import net.bodz.lily.model.sea.QVariantMap;

import com.bee32.zebra.oa.accnt.DebitOrCredit;

public class AccountingEntryCriteria
        extends CoMomentIntervalCriteria {

    public Long eventId;
    public Integer accountId;
    public DebitOrCredit side;
    public DoubleRange valueRange;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public boolean isDebitSide() {
        return side == DebitOrCredit.DEBIT;
    }

    public boolean isCreditSide() {
        return side == DebitOrCredit.CREDIT;
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
        eventId = map.getLong("doc", eventId);
        accountId = map.getInt("account", accountId);
        side = map.getPredef(DebitOrCredit.class, "side", side);
        valueRange = map.getDoubleRange("values", valueRange);
    }

}
