package com.bee32.sem.track.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 问题参考链接
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_href_seq", allocationSize = 1)
public class IssueHref
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int URL_LENGTH = 200;

    private Issue issue;
    private String url;

    @ManyToOne(optional = false)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    @Column(nullable = false, length = URL_LENGTH)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url == null)
            throw new NullPointerException("url");
        this.url = url;
    }

}
