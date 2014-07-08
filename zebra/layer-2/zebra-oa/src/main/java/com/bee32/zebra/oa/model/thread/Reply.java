package com.bee32.zebra.oa.model.thread;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.tinylily.model.mx.base.Message;

public class Reply
        extends Message {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Reply parent;
    private List<PropertyChangeEvent> changes;

    public Reply(Topic topic, Reply parent) {
        if (topic == null)
            throw new NullPointerException("topic");
        this.topic = topic;
        this.parent = parent;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        if (topic == null)
            throw new NullPointerException("topic");
        this.topic = topic;
    }

    public Reply getParent() {
        return parent;
    }

    public void setParent(Reply parent) {
        this.parent = parent;
    }

}
