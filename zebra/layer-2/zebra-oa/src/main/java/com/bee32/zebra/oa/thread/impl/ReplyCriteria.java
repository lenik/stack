package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.mx.base.CoMessageCriteria;
import com.tinylily.model.sea.QVariantMap;

public class ReplyCriteria
        extends CoMessageCriteria {

    public Integer topicId;
    public Integer parentId;
    public Integer partyId;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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
        topicId = map.getInt("topic", topicId);
        parentId = map.getInt("parent", parentId);
        partyId = map.getInt("party", partyId);
    }

}
