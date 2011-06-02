package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.PinkEntity;
import com.bee32.sem.people.entity.Party;

@Entity
public class ChanceAction
        extends PinkEntity<Long> {

    private static final long serialVersionUID = 1L;

    boolean plan;
    List<Party> parties;

    ChanceActionStyle style;

    Date beginTime;
    Date endTime;
    String content;
    String spending;
    Chance chance;
    ChanceStage stage;

    /**
     * 工作日志类型
     */
    @Column(nullable = false)
    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    /**
     * 对应客户
     */
    @OneToMany
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        if (parties == null)
            parties = new ArrayList<Party>();
        this.parties = parties;
    }

    /**
     * 洽谈方式
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public ChanceActionStyle getStyle() {
        return style;
    }

    public void setStyle(ChanceActionStyle style) {
        if (style == null)
            throw new NullPointerException("can't set null to ChanceAction.style");
        this.style = style;
    }

    /**
     * 开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        if (beginTime == null)
            throw new NullPointerException("can't set null to ChanceAction.beginTime");
        this.beginTime = beginTime;
    }

    /**
     * 结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 拜访目的（计划）或洽谈内容（日志）
     */
    @Column(length = 500, nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null)
            content = "";
        this.content = content;
    }

    /**
     * 产生费用明细
     */
    @Column(length = 300, nullable = false)
    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        if (spending == null)
            spending = "";
        this.spending = spending;
    }

    /**
     * 对应机会
     */
    @ManyToOne(optional = true)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 阶段推进
     */
    @ManyToOne
    public ChanceStage getChanceStage() {
        return stage;
    }

    public void setChanceStage(ChanceStage stage) {
        this.stage = stage;
    }
}
