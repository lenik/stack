package com.bee32.zebra.oa.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.IMomentInterval;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.mx.base.CoMessage;

public class Topic
        extends CoMessage
        implements IMomentInterval {

    private static final long serialVersionUID = 1L;

    private CategoryDef category;
    private PhaseDef phase;
    private Date beginTime;
    private Date endTime;
    private double value;
    // private double minValue, maxValue; // for estimated values.

    private Set<String> tags = new TreeSet<>();
    private List<TopicParty> parties = new ArrayList<TopicParty>();
    private List<Reply> replies = new ArrayList<>();

    /**
     * @label 分类
     */
    @OfGroup(OaGroups.Classification.class)
    public CategoryDef getCategory() {
        return category;
    }

    public void setCategory(CategoryDef category) {
        this.category = category;
    }

    /**
     * @label 阶段
     */
    @OfGroup(OaGroups.Classification.class)
    public PhaseDef getPhase() {
        return phase;
    }

    public void setPhase(PhaseDef phase) {
        this.phase = phase;
    }

    /**
     * @label 开始时间
     */
    @OfGroup(OaGroups.Schedule.class)
    @Override
    public Date getBeginTime() {
        return beginTime;
    }

    @Override
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @label 结束时间
     */
    @OfGroup(OaGroups.Schedule.class)
    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Value
     * 
     * @label Value
     * @label.zh.cn 价值
     */
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @OfGroup(OaGroups.Classification.class)
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

    @OfGroup(OaGroups.UserInteraction.class)
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

}
