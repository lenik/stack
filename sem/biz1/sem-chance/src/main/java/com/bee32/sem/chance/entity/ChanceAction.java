package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.Strings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.MomentInterval;
import com.bee32.plover.orm.ext.color.Pink;
import com.bee32.sem.calendar.ICalendarEvent;
import com.bee32.sem.people.entity.Party;

@Entity
@Pink
public class ChanceAction
        extends MomentInterval
        implements ICalendarEvent, Comparable<ChanceAction> {

    private static final long serialVersionUID = 1L;

    boolean plan;
    List<Party> parties;

    User actor;
    ChanceActionStyle style;

    String content;
    String spending;
    Chance chance;
    ChanceStage stage;

    public ChanceAction() {
        super();
        parties = new ArrayList<Party>();
    }

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
    @ManyToMany
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        if (parties == null)
            parties = new ArrayList<Party>();
        this.parties = parties;
    }

    /**
     * 行动人
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        if (actor == null)
            throw new NullPointerException("can't set Null to ChanceAction.actor");
        this.actor = actor;
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
    @ManyToOne
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
    public ChanceStage getStage() {
        return stage;
    }

    public void setStage(ChanceStage stage) {
        this.stage = stage;
    }

    @Transient
    public void pushToStage(ChanceStage stage) {
        if (stage == null)
            throw new NullPointerException("stage");
        if (stage.getOrder() >= getStage().getOrder())
            this.stage = stage;
    }

    /**
     * 【工作日志】AAA...
     */
    @Transient
    @Override
    public String getSubject() {
        return Strings.ellipse(content, 25);
    }

    @Transient
    @Override
    public List<String> getStyles() {
        return Collections.emptyList();
    }

    @Override
    public int compareTo(ChanceAction o) {
        Date time1 = getBeginTime();
        Date time2 = o.getBeginTime();
        int cmp = time1.compareTo(time2);
        if (cmp != 0)
            return cmp;
        int hash1 = System.identityHashCode(this);
        int hash2 = System.identityHashCode(o);
        cmp = hash1 - hash2;
        return cmp;
    }

}
