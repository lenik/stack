package com.bee32.sem.track.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DefaultValue;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 事件参与者
 *
 * 对事件感兴趣或负责的用户列表。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_observer_seq", allocationSize = 1)
public class IssueObserver
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int STATE_TEXT_LENGTH = 100;

    Issue issue;
    Principal observer;
    int rank = 1;
    boolean manager;
    boolean fav;
    boolean read;
    String stateText = "";

    /**
     * 事件
     *
     * 相关的事件。
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    /**
     * 用户
     *
     * 对事件感兴趣的用户。
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Principal getObserver() {
        return observer;
    }

    public void setObserver(Principal observer) {
        if (observer == null)
            throw new NullPointerException("observer");
        this.observer = observer;
    }

    /**
     * 权重级别
     *
     * 这个数字用作和一些统计、投票相关数字相乘。一般，被指定的参与者级别为1，来宾、第三方参与者级别为0。
     */
    @Column(nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 管理标志
     *
     * 用户是否对事件有管理权。
     */
    @Column(nullable = false)
    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    /**
     * 收藏标志
     *
     * 用户是否收藏了该事件。
     */
    @Column(nullable = false)
    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    /**
     * 阅读标志
     *
     * 用户是否阅读/打开了该事件。
     */
    @Column(nullable = false)
    @DefaultValue("false")
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Column(nullable = false, length = STATE_TEXT_LENGTH)
    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        if (stateText == null)
            throw new NullPointerException("stateText");
        this.stateText = stateText;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(issue), //
                naturalId(observer));
    }

}
