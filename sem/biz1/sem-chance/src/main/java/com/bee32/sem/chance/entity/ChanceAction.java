package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.PinkEntity;
import com.bee32.sem.people.entity.Party;

@Entity
public class ChanceAction
        extends PinkEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 工作日志类型
     */
    boolean plan;

    /**
     * 对应客户
     */
    List<Party> parties;

    /**
     * 洽谈方式
     */
    String style;

    /**
     * 开始时间
     */
    Date beginTime;

    /**
     * 结束时间
     */
    Date endTime;

    /**
     * 拜访目的（计划）或洽谈内容（日志）
     */
    String content;

    /**
     * 产生费用明细
     */
    String spending;

    /**
     * 对应机会
     */
    Chance chance;

    /**
     * 阶段推进
     */
    ChanceStage chanceStage;

    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    @ManyToOne(optional = true)
    public Chance getSalesChance() {
        return chance;
    }

    @Column(length = 10)
    public void setChance(Chance chance) {
        this.chance = chance;
    }

    public ChanceStage getChanceStage() {
        return chanceStage;
    }

    public void setChanceStage(ChanceStage chanceStage) {
        this.chanceStage = chanceStage;
    }
}
