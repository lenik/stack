package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 收藏状态
 *
 * 是否已将该问题加入到用户的问题收藏夹。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_fav_seq", allocationSize = 1)
public class IssueFav
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Issue issue;
    User user;

    @ManyToOne(optional = false)
    @Cascade({ CascadeType.REFRESH, CascadeType.SAVE_UPDATE })
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @ManyToOne(optional = false)
    @Cascade(CascadeType.REFRESH)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
