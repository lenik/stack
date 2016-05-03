package com.bee32.zebra.oa.thread;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.schema.CategoryDef;
import net.bodz.lily.model.base.schema.PhaseDef;
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.mx.base.CoMessage;

import com.bee32.zebra.oa.OaGroups;

/**
 * 项目/机会
 */
@IdType(Integer.class)
// @SchemaId(Schemas.OPPORTUNITY)
public class Topic
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    private double value;
    // private double minValue, maxValue; // for estimated values.

    private List<TopicParty> parties;
    private List<Reply> replies;

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_COOP);
        parties = new ArrayList<TopicParty>();
        replies = new ArrayList<Reply>();
    }

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
     * 估计的价值。
     * 
     * 对于项目来说，应是已确定的合同金额；对潜在的机会来说，则是估算潜在的价值。
     * 
     * @label Value
     * @label.zh （估）价值
     */
    // @OfGroup(StdGroup.Ranking.class)
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
