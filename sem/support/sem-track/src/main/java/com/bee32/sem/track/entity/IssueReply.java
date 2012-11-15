package com.bee32.sem.track.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.people.entity.Person;

@Entity
public class IssueReply
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 4000;

    Person replier;
    Issue issue;
    String text = "";

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
