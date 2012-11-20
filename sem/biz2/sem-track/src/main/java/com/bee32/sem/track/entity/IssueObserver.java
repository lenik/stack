package com.bee32.sem.track.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAuto;

/**
 * 问题观察员
 *
 * 对问题感兴趣或负责的用户列表。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_observer_seq", allocationSize = 1)
public class IssueObserver
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Issue issue;
    User user;
    boolean admin;
    boolean manager;
    boolean fav;

    /**
     * 问题
     *
     * 相关的问题。
     */
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
     * 对问题感兴趣或负责的用户。
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
     * 超级管理员状态
     *
     * 用户是否对问题有超级管理权，超级管理权可以删除问题。
     */
    @Column(nullable = false)
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    /**
     * 收藏状态
     *
     * 是否已将该问题加入到用户的问题收藏夹。
     */
    @Column(nullable = false)
    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

}
