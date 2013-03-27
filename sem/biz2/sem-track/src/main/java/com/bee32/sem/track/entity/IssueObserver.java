package com.bee32.sem.track.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 问题观察员
 *
 * 对问题感兴趣或负责的用户列表。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_observer_seq", allocationSize = 1)
public class IssueObserver
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Issue issue;
    Principal observer;
    boolean manager;
    boolean fav;

    /**
     * 问题
     *
     * 相关的问题。
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
     * 对问题感兴趣的用户。
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
     * 管理标志
     *
     * 用户是否对问题有管理权。
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
     * 用户是否收藏了该问题。
     */
    @Column(nullable = false)
    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(issue), //
                naturalId(observer));
    }

}
