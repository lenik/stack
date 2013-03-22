package com.bee32.sem.track.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.mail.entity.Message;

/**
 * 问题
 *
 * 需要解决的问题。
 *
 * -- 用 issue 而不用 topic, question，以突出这个问题是需要解决的具有任务性质的。
 */
@Entity
@DiscriminatorValue("ISSU")
public class Issue
        extends Message {

    private static final long serialVersionUID = 1L;

    public static final int COMMITISH_LENGTH = 32;
    public static final int TAGS_LENGTH = 100;

    String tags = "";
    String commitish;

    List<UserFile> attachments = new ArrayList<UserFile>();
    List<IssueObserver> observers = new ArrayList<IssueObserver>();

    Chance chance;
    StockOrder stockOrder;

    /**
     * 状态
     *
     * 问题的当前状态，如已解决、挂起等。
     */
    @Transient
    public IssueState getIssueState() {
        int state = getState();
        IssueState issueState = IssueState.forValue((char) state);
        return issueState;
    }

    public void setIssueState(IssueState issueState) {
        int state = issueState.getValue();
        setState(state);
    }

    /**
     * 详细信息
     *
     * 详细描述当前存在的问题。
     */
    @Transient
    public String getIssueText() {
        return getText();
    }

    public void setIssueText(String issueText) {
        setText(issueText);
    }

    /**
     * 标签集
     *
     * 问题关联的标签集，用空格分隔。
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
     * 和问题相关的提交标识。（未使用）
     */
    @Column(length = COMMITISH_LENGTH)
    public String getCommitish() {
        return commitish;
    }

    public void setCommitish(String commitish) {
        this.commitish = commitish;
    }

    /**
     * 相关附件
     *
     * 和问题相关的附件，如屏幕截图、产品缺陷照片等等。
     */
    @ElementCollection
    @JoinTable(name = "IssueAttachment", //
    /*        */joinColumns = @JoinColumn(name = "issue"),
    /*        */inverseJoinColumns = @JoinColumn(name = "userFile"))
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    @OneToMany(mappedBy = "issue", orphanRemoval = true)
    @Cascade({ CascadeType.DELETE, CascadeType.PERSIST, CascadeType.SAVE_UPDATE })
    public List<IssueObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<IssueObserver> observers) {
        this.observers = observers;
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
