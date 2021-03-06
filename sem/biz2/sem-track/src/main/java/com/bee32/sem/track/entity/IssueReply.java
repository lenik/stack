package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.principal.User;
import com.bee32.sem.mail.entity.Message;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_reply_seq", allocationSize = 1)
public class IssueReply
        extends Message<IssueReply> {

    private static final long serialVersionUID = 1L;

    private Issue issue;
    private User user;

    @ManyToOne(optional = false)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        this.user = user;
    }

}
