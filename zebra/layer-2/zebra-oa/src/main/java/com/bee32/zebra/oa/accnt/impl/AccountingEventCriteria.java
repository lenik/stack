package com.bee32.zebra.oa.accnt.impl;

import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.lily.model.mx.base.CoMessageCriteria;
import net.bodz.lily.model.sea.QVariantMap;

public class AccountingEventCriteria
        extends CoMessageCriteria {

    public Integer topicId;
    public Integer partyId;
    public Set<String> tags;
    public DoubleRange debitTotalRange;
    public DoubleRange creditTotalRange;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

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
        topicId = map.getInt("topic", topicId);
        partyId = map.getInt("party", partyId);
        debitTotalRange = map.getDoubleRange("debits", debitTotalRange);
        creditTotalRange = map.getDoubleRange("credits", creditTotalRange);
        String tagsStr = map.getString("tags");
        if (tagsStr != null)
            tags = new TreeSet<String>();
    }

}
