package com.bee32.zebra.oa.thread.impl;

import java.util.Set;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DateRange;

import com.tinylily.model.mx.base.CoMessageCriteria;
import com.tinylily.model.sea.QVariantMap;

public class ReplyCriteria
        extends CoMessageCriteria {

    Set<Integer> topics;
    Set<Integer> parties;
    DateRange dateRange;

    public Set<Integer> getTopics() {
        return topics;
    }

    public void setTopics(Set<Integer> topics) {
        this.topics = topics;
    }

    public Set<Integer> getParties() {
        return parties;
    }

    public void setParties(Set<Integer> parties) {
        this.parties = parties;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        topics = map.getInts("topic", topics);
        parties = map.getInts("parties", parties);
        dateRange = map.getDateRange("dates", dateRange);
    }

}
