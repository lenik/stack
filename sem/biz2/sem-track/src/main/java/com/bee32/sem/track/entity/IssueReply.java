package com.bee32.sem.track.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Index;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.color.MomentInterval;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "reply_seq", allocationSize = 1)
public class IssueReply
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 4000;

    User replier;
    Issue issue;
    String text = "";

    @ManyToOne
    public User getReplier() {
        return replier;
    }

    public void setReplier(User replier) {
        this.replier = replier;
    }

    @ManyToOne
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    /**
     * Opt: Maybe full-text search is more apropriate?
     */
    @Index(name = "issue_reply_text")
    @Column(length = TEXT_LENGTH, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null)
            throw new NullPointerException("text");
        this.text = text;
    }

}
