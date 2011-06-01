package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;

import com.bee32.plover.orm.ext.color.PinkEntity;
import com.bee32.sem.people.entity.Party;

@Entity
public class ChanceAction
        extends PinkEntity<Long> {

    private static final long serialVersionUID = 1L;

    boolean plan;
    List<Party> parties;
    ChanceContactStyle style;
    Date beginTime;
    Date endTime;
    String content;
    String spending;
    Chance chance;
    ChanceStage stage;

    /**
     * 工作日志类型
     */
    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    /**
     * 对应客户
     */
    @CollectionOfElements
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    /**
     * 洽谈方式
     */
    public ChanceContactStyle getStyle() {
        return style;
    }

    public void setStyle(ChanceContactStyle style) {
        this.style = style;
    }

    /**
     * 开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
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
    @Column(length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 产生费用明细
     */
    @Column(length = 300)
    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    /**
     * 对应机会
     */
    @ManyToOne(optional = true)
    public Chance getSalesChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 阶段推进
     */
    @ManyToOne(optional = true)
    public ChanceStage getChanceStage() {
        return stage;
    }

    public void setChanceStage(ChanceStage stage) {
        this.stage = stage;
    }
}
