package com.bee32.zebra.oa.thread;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.entity.IMomentInterval;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.mx.CoMessage;

import com.bee32.zebra.oa.OaGroups;
import com.bee32.zebra.oa.contact.Person;

/**
 * 项目跟进
 */
@IdType(Integer.class)
public class Reply
        extends CoMessage<Integer>
        implements IMomentInterval {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Reply parent;

    private ReplyCategory category;
    private ReplyPhase phase;

    private List<Person> parties;
    private List<PropertyChangeEvent> changes;

    public Reply() {
    }

    public Reply(Topic topic, Reply parent) {
        this.topic = topic;
        this.parent = parent;
    }

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_SHARED);
        parties = new ArrayList<>();
        changes = new ArrayList<>();
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

    /**
     * @label Category
     * @label.zh 分类
     */
    @OfGroup(StdGroup.Classification.class)
    public ReplyCategory getCategory() {
        return category;
    }

    public void setCategory(ReplyCategory category) {
        this.category = category;
    }

    /**
     * 阶段
     */
    @OfGroup(StdGroup.Status.class)
    public ReplyPhase getPhase() {
        return phase;
    }

    public void setPhase(ReplyPhase phase) {
        this.phase = phase;
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
