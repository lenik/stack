package com.bee32.sem.track.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
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
    User user;
    boolean manager;
    boolean fav;

    /**
     * 问题
     *
     * 相关的问题。
     */
    @ManyToOne(optional = false)
    @Cascade(CascadeType.REFRESH)
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
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        this.user = user;
    }

    /**
     * 管理员状态
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

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

}
