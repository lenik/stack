package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
public class Chance
        extends GreenEntity<Long> {

    private static final long serialVersionUID = 1L;

    private ChanceCategory category;
    private String title;
    private String source;
    private String content;
    private User responsible;
    private Date createDate;
    private ChanceStage stage;

    private List<ChanceParty> parties;
    private List<ChanceAction> actions;

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
    @Column(length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 来源
     */
    @Column(length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 机会内容
     */
    @Column(length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 负责人
     */
    @ManyToOne
    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    /**
     * 发现时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToOne(optional = true)
    public ChanceStage getStage() {
        return stage;
    }

    public void setStage(ChanceStage stage) {
        this.stage = stage;
    }

    @OneToMany(mappedBy = "chance")
    public List<ChanceParty> getParties() {
        return parties;
    }

    public void setParties(List<ChanceParty> parties) {
        this.parties = parties;
    }

    @OneToMany(mappedBy = "chance")
    public List<ChanceAction> getActions() {
        return actions;
    }

    public void setActions(List<ChanceAction> actions) {
        this.actions = actions;
    }

}
