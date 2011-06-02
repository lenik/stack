package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.GreenEntity;

/**
 * 销售机会
 */
@Entity
public class Chance
        extends GreenEntity<Long> {

    private static final long serialVersionUID = 1L;

    User owner;
    ChanceCategory category;
    ChanceSource source;
    String subject;
    String content;

    Date createDate;

    Set<ChanceParty> parties;
    List<ChanceAction> actions;

    ChanceStage stage;

    public Chance() {
         parties = new HashSet<ChanceParty>();
         actions = new ArrayList<ChanceAction>();
    }

    /**
     * 类型
     */
    @ManyToOne(optional = true)
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
        this.subject = subject;
    }

    /**
     * 来源
     */
    @ManyToOne(optional = true)
    public ChanceSource getSource() {
        return source;
    }

    public void setSource(ChanceSource source) {
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
            content = "";
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

    /**
     * 发现时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        if (createDate == null)
            throw new NullPointerException("cant set Null to Chance.createDate");
        this.createDate = createDate;
    }

    @OneToMany(mappedBy = "chance")
    public Set<ChanceParty> getParties() {
        return parties;
    }

    public void setParties(Set<ChanceParty> parties) {
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
// if(actions == null)
// throw new NullPointerException("can't set Null to Chance.actions");
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
    void setStage(ChanceStage stage) {
        if (stage == null)
            throw new NullPointerException("stage");
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
    public void addAction(ChanceAction actoin) {
        List<ChanceAction> caList = getActions();
        caList.add(actoin);

        // sort the list
        int size = caList.size();
        List<ChanceAction> sortedList = new ArrayList<ChanceAction>();
        for (int index = 0; index < size; index++) {
            ChanceAction ca = caList.get(index);
            for (int j = index + 1; j < size; j++) {
                ChanceAction temp = caList.get(j);
                if (temp.beginTime.before(ca.beginTime))
                    ca = temp;
            }
            sortedList.add(ca);
        }
        setActions(sortedList);
    }

}
