package com.bee32.sem.track.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.mail.entity.Message;
import com.bee32.sem.track.util.IssueState;
import com.bee32.sem.track.util.IssueType;

/**
 * 事件
 *
 * 需要解决的事件。
 *
 * -- 用 issue 而不用 topic, question，以突出这个事件是需要解决的具有任务性质的。
 */
@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "issue_seq", allocationSize = 1)
public class Issue
        extends Message<Issue> {

    private static final long serialVersionUID = 1L;

    public static final int COMMITISH_LENGTH = 32;
    public static final int TAGS_LENGTH = 100;

    private IssueType issueType = IssueType.ISSUE;
    private Date dueDate;
    private Date endTime;

    private String tags = "";
    private String commitish;

    private List<IssueHref> hrefs = new ArrayList<IssueHref>();
    private List<IssueAttachment> attachments = new ArrayList<IssueAttachment>();
    private List<IssueObserver> observers = new ArrayList<IssueObserver>();
    private List<IssueReply> replies = new ArrayList<IssueReply>();
    private IssueCounter counter;

    private Chance chance;
    private StockOrder stockOrder;

    public Issue() {
        counter = new IssueCounter();
        counter.setIssue(this);
    }

    @DefaultValue("'I'")
    @Column(name = "type", nullable = false)
    public char getTypeChar() {
        return issueType.getValue();
    }

    public void setTypeChar(char type) {
        this.issueType = IssueType.forValue(type);
    }

    @Transient
    public IssueType getType() {
        return issueType;
    }

    public void setType(IssueType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.issueType = type;
    }

    /**
     * 状态
     *
     * 事件的当前状态，如已解决、挂起等。
     */
    @Transient
    public IssueState getIssueState() {
        int stateInt = getStateInt();
        IssueState state = IssueState.forValue((char) stateInt);
        return state;
    }

    public void setIssueState(IssueState issueState) {
        int stateInt = issueState.getValue();
        setStateInt(stateInt);
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 标签集
     *
     * 事件关联的标签集，用空格分隔。
     */
    @Column(length = TAGS_LENGTH)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

    /**
     * 提交标识
     *
     * 和事件相关的提交标识。（未使用）
     */
    @Column(length = COMMITISH_LENGTH)
    public String getCommitish() {
        return commitish;
    }

    public void setCommitish(String commitish) {
        this.commitish = commitish;
    }

    @OneToMany(mappedBy = "issue", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<IssueHref> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<IssueHref> hrefs) {
        if (hrefs == null)
            throw new NullPointerException("hrefs");
        this.hrefs = hrefs;
    }

    /**
     * 相关附件
     *
     * 和事件相关的附件，如屏幕截图、产品缺陷照片等等。
     */
    @OneToMany(mappedBy = "issue", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<IssueAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<IssueAttachment> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    @OneToMany(mappedBy = "issue", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<IssueObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<IssueObserver> observers) {
        if (observers == null)
            throw new NullPointerException("observers");
        this.observers = observers;
    }

    @OneToMany(mappedBy = "issue", orphanRemoval = true)
    @OrderBy("createdDate ASC")
    @Cascade(CascadeType.ALL)
    public List<IssueReply> getReplies() {
        return replies;
    }

    public void setReplies(List<IssueReply> replies) {
        if (replies == null)
            throw new NullPointerException("replies");
        this.replies = replies;
    }

    @OneToOne(mappedBy = "issue", optional = false, orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public IssueCounter getCounter() {
        return counter;
    }

    public void setCounter(IssueCounter counter) {
        if (counter == null) {
            counter = new IssueCounter();
            counter.setIssue(this);
        }
        this.counter = counter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public StockOrder getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrder stockOrder) {
        this.stockOrder = stockOrder;
    }

}
