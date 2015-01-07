package com.bee32.zebra.oa.thread;

import java.beans.PropertyChangeEvent;
import java.util.List;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.oa.OaGroups;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.IMomentInterval;
import com.tinylily.model.mx.base.CoMessage;

public class Reply
        extends CoMessage<Integer>
        implements IMomentInterval {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Reply parent;
    private List<Person> parties;
    private List<PropertyChangeEvent> changes;

    public Reply() {
    }

    public Reply(Topic topic, Reply parent) {
        this.topic = topic;
        this.parent = parent;
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

    @OfGroup(OaGroups.UserInteraction.class)
    public List<Person> getParties() {
        return parties;
    }

    public void setParties(List<Person> parties) {
        this.parties = parties;
    }

    @DetailLevel(DetailLevel.EXPERT2)
    public List<PropertyChangeEvent> getChanges() {
        return changes;
    }

    public void setChanges(List<PropertyChangeEvent> changes) {
        this.changes = changes;
    }

}
