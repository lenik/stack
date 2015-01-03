package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.mx.base.CoMessageCriteria;
import com.tinylily.model.sea.QVariantMap;

public class ReplyCriteria
        extends CoMessageCriteria {

    public Long topicId;
    public Long parentId;
    public Integer partyId;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        topicId = map.getLong("topic", topicId);
        parentId = map.getLong("parent", parentId);
        partyId = map.getInt("party", partyId);
    }

}
