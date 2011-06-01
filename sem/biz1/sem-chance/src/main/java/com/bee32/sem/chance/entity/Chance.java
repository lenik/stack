package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
<<<<<<< HEAD
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
=======
>>>>>>> polish Chance again
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
public class Chance
        extends GreenEntity<Long> {

    private static final long serialVersionUID = 1L;

    User owner;

    ChanceCategory category;
    String source;
    String subject;
    String content;

    Date createDate;

    List<ChanceParty> parties;
    List<ChanceAction> actions;

    ChanceStage stage;

    public Chance() {
        // parties = new ArrayList<ChanceParty>();
        // histories = new ArrayList<ChanceAction>();
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
    public String getTitle() {
        return subject;
    }

    public void setTitle(String title) {
        this.subject = title;
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
        if(content == null)
            content = "";
        this.content = content;
    }

    /**
     * 负责人
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public User getResponsible() {
        return owner;
    }

    public void setResponsible(User responsible) {
        this.owner = responsible;
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
        if(createDate == null )
            throw new NullPointerException("cant set null to Chance.createDate");
        this.createDate = createDate;
    }

    @OneToMany(mappedBy = "chance")
    public List<ChanceParty> getParties() {
        return parties;
    }

    public void setParties(List<ChanceParty> parties) {
        if(parties == null)
            throw new NullPointerException("can't set Null to Chance.parties");
        this.parties = parties;
    }

    @OneToMany(mappedBy = "chance")
    @OrderBy("beginTime")
    public List<ChanceAction> getActions() {
        return actions;
    }

    public void setActions(List<ChanceAction> actions) {
        if(actions == null)
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
    void setStage(ChanceStage stage) {
        if (stage == null)
            throw new NullPointerException("stage");
        this.stage = stage;
    }

    /**
     * TODO 获得最近一次行动。
     *
     * @return 最近一次行动，如果还没有任何行动返回 <code>null</code>.
     */
    @Transient
    public ChanceAction getLatestAction() {
        return null;
    }

}
