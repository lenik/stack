package com.bee32.zebra.oa.thread;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.bee32.zebra.oa.contact.Party;
import com.tinylily.model.base.IMomentInterval;
import com.tinylily.model.mx.base.CoMessage;

public class Reply
        extends CoMessage
        implements IMomentInterval {

    private static final long serialVersionUID = 1L;

    private long id;
    private Topic topic;
    private Reply parent;
    private List<PropertyChangeEvent> changes;
    private List<Party> parties;

    public Reply(Topic topic, Reply parent) {
        this.topic = topic;
        this.parent = parent;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @label 主题
     */
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        if (topic == null)
            throw new NullPointerException("topic");
        this.topic = topic;
    }

    /**
     * @label 上一级
     */
    public Reply getParent() {
        return parent;
    }

    public void setParent(Reply parent) {
        this.parent = parent;
    }

    // @PropertyGroup(OaGroups.UserInteraction.class)
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

}
