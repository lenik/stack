package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.lily.model.mx.CoMessageMask;

public class ReplyMask
        extends CoMessageMask {

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
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        topicId = map.getInt("topic", topicId);
        parentId = map.getInt("parent", parentId);
        partyId = map.getInt("party", partyId);
    }

}
