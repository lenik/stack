package com.bee32.zebra.oa.thread;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.IdType;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.base.security.User;
import com.tinylily.model.mx.base.CoMessage;

/**
 * 项目/机会
 */
@IdType(Integer.class)
public class Topic
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    private double value;
    // private double minValue, maxValue; // for estimated values.

    private List<TopicParty> parties = new ArrayList<TopicParty>();
    private List<Reply> replies = new ArrayList<>();

    /**
     * 业务员
     */
    @Override
    // @OfGroup(OaGroups.UserInteraction.class)
    public User getOp() {
        return super.getOp();
    }

    /**
     * 分类
     */
    @OfGroup(StdGroup.Classification.class)
    public CategoryDef getCategory() {
        return super.getCategory();
    }

    public void setCategory(CategoryDef category) {
        super.setCategory(category);
    }

    /**
     * 阶段
     */
    @OfGroup(StdGroup.Status.class)
    public PhaseDef getPhase() {
        return super.getPhase();
    }

    public void setPhase(PhaseDef phase) {
        super.setPhase(phase);
    }

    /**
     * Value
     * 
     * @label Value
     * @label.zh 价值
     */
    @OfGroup(StdGroup.Ranking.class)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * 参与者
     */
    public List<TopicParty> getParties() {
        return parties;
    }

    public void setParties(List<TopicParty> parties) {
        this.parties = parties;
    }

    /**
     * 跟进
     */
    @OfGroup(OaGroups.UserInteraction.class)
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

}
