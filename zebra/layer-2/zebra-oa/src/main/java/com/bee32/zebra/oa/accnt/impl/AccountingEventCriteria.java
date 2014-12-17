package com.bee32.zebra.oa.accnt.impl;

import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;

import com.tinylily.model.base.CoMomentIntervalCriteria;
import com.tinylily.model.sea.QVariantMap;

public class AccountingEventCriteria
        extends CoMomentIntervalCriteria {

    Integer partyId;
    Set<String> tags;
    DoubleRange debitTotalRange;
    DoubleRange creditTotalRange;

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public DoubleRange getDebitTotalRange() {
        return debitTotalRange;
    }

    public void setDebitTotalRange(DoubleRange debitTotalRange) {
        this.debitTotalRange = debitTotalRange;
    }

    public DoubleRange getCreditTotalRange() {
        return creditTotalRange;
    }

    public void setCreditTotalRange(DoubleRange creditTotalRange) {
        this.creditTotalRange = creditTotalRange;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        partyId = map.getInt("party", partyId);
        debitTotalRange = map.getDoubleRange("debits", debitTotalRange);
        creditTotalRange = map.getDoubleRange("credits", creditTotalRange);
        String tagsStr = map.getString("tags");
        if (tagsStr != null)
            tags = new TreeSet<String>();
    }

}
