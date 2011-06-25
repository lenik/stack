package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 销售机会
 */
@Entity
@Green
public class Chance
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    User owner;
    ChanceCategory category = ChanceCategory.NORMAL;
    ChanceSourceType source = ChanceSourceType.OTHER;
    String subject = "";
    String content = "";

    List<ChanceParty> parties = new ArrayList<ChanceParty>();
    List<ChanceAction> actions = new ArrayList<ChanceAction>();

    // Redundant
    ChanceStage stage = ChanceStage.INIT;

    /**
     * 类型
     */
    @ManyToOne
    public ChanceCategory getCategory() {
        return category;
    }

    public void setCategory(ChanceCategory category) {
        this.category = category;
    }

    /**
     * 机会主题
     */
    @Column(length = 100, nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null)
            throw new NullPointerException("can't set Null to Chance.subject");
        this.subject = subject;
    }

    /**
     * 来源
     */
    @ManyToOne
    public ChanceSourceType getSource() {
        return source;
    }

    public void setSource(ChanceSourceType source) {
        if (source == null)
            throw new NullPointerException("can't set Null to Chance.source");
        this.source = source;
    }

    /**
     * 机会内容
     */
    @Column(length = 500, nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null)
            throw new NullPointerException("content");
        this.content = content;
    }

    /**
     * 负责人
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToMany(mappedBy = "chance")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<ChanceParty> getParties() {
        return parties;
    }

    public void setParties(List<ChanceParty> parties) {
        if (parties == null)
            throw new NullPointerException("can't set Null to Chance.parties");
        this.parties = parties;
    }

    @OneToMany(mappedBy = "chance")
    @OrderBy("beginTime")
    public List<ChanceAction> getActions() {
        return actions;
    }

    public void setActions(List<ChanceAction> actions) {
        if (actions == null)
            throw new NullPointerException("can't set Null to Chance.actions");
        this.actions = actions;
    }

    /**
     * 获取机会阶段/进度（冗余）。
     *
     * @return 机会最后一次更新的进度，如果尚无更新，应返回一个非空的初始进度。
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public ChanceStage getStage() {
        return stage;
    }

    /**
     * 机会进度只能通过日志项来改变，本对象中的进度为冗余。
     */
    public void setStage(ChanceStage stage) {
// if (stage == null)
// throw new NullPointerException("stage");
        this.stage = stage;
    }

    /**
     *
     * @return 最近一次行动，如果还没有任何行动返回 <code>null</code>.
     */
    @Transient
    public ChanceAction getLatestAction() {
        ChanceAction ca = null;
        if (getActions() != null) {
            ca = getActions().get(0);
            for (ChanceAction item : getActions()) {
                if (ca.getBeginTime().before(item.getBeginTime()))
                    ca = item;
            }
// int lastIndex = getActions().size() - 1;
// ca = getActions().get(lastIndex);
        }
        return ca;
    }

    @Transient
    public void addAction(ChanceAction action) {
        List<ChanceAction> actionList = getActions();
        actionList.add(action);
        Collections.sort(actionList);
    }

}
