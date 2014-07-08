package com.bee32.zebra.oa.model.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.tinylily.model.base.IMomentInterval;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.mx.base.Message;

public class Topic
        extends Message
        implements IMomentInterval {

    private static final long serialVersionUID = 1L;

    private CategoryDef category;
    private PhaseDef phase;

    private Date beginTime;
    private Date endTime;
    private double value;
    // private double minValue;
    // private double maxValue;

    private Set<String> tags = new TreeSet<>();
    private List<TopicParty> parties = new ArrayList<TopicParty>();
    private List<Reply> replies = new ArrayList<>();

    public CategoryDef getCategory() {
        return category;
    }

    public void setCategory(CategoryDef category) {
        this.category = category;
    }

    public PhaseDef getPhase() {
        return phase;
    }

    public void setPhase(PhaseDef phase) {
        this.phase = phase;
    }

    @Override
    public Date getBeginTime() {
        return beginTime;
    }

    @Override
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<TopicParty> getParties() {
        return parties;
    }

    public void setParties(List<TopicParty> parties) {
        this.parties = parties;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

}
